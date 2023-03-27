package com.minicloud.simulate.order.service.impl;


import cn.hutool.json.JSONUtil;
import com.minicloud.common.core.util.GuidUtil;
import com.minicloud.goods.dto.GoodsItemSubDTO;
import com.minicloud.goods.feign.RemoteSimulateGoodsService;
import com.minicloud.simulate.order.cluster.ClusterServiceInvoker;
import com.minicloud.simulate.order.entity.OrderEntity;
import com.minicloud.simulate.order.entity.OrdersEntity;
import com.minicloud.simulate.order.mapper.OrderMapper;
import com.minicloud.simulate.order.mapper.OrdersMapper;
import com.minicloud.simulate.order.mq.MqMessageSender;
import com.minicloud.simulate.order.quene.OrderWithoutPayQuene;
import com.minicloud.simulate.order.service.OrderService;
import com.minicloud.simulate.order.wrapper.OrderUpdate;
import io.seata.core.context.RootContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @Author alan.wang
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Resource
    private OrdersMapper ordersMapper;

    //private final OrderMapperorderMapper;

    @Resource
    private  RedisTemplate redisTemplate;

    @Resource
    private  RemoteSimulateGoodsService remoteSimulateGoodsService;

    @Resource
    private  MqMessageSender mqMessageSender;

    private OrderWithoutPayQuene orderWithoutPayQuene;

    private ClusterServiceInvoker clusterServiceInvoker;

    @Resource(name = "mqOrderWithoutPayQuene")
    public void setOrderWithoutPayQuene(OrderWithoutPayQuene delayQueueOrderWithoutPayQuene) {
        this.orderWithoutPayQuene = delayQueueOrderWithoutPayQuene;
    }

    @Resource(name = "nacosClusterServiceInvoker")
    public void setClusterServiceInvoker(ClusterServiceInvoker nacosClusterServiceInvoker) {
        this.clusterServiceInvoker = nacosClusterServiceInvoker;
    }

    /**
     * 创建订单demo代码
     * 模拟创建商品1库存为10和商品2库存为20创建订单
     * 首先用redis+lua脚本尝试扣减库存
     * 如果全部成功则发送mq消息
     * 如果有一个失败则直接返回失败
     *
     * */
    @Override
    public long createOrder() throws Exception {

        //生成订单号
        long orderId = GuidUtil.longGuid();

        //模拟创建订单商品和库存
        GoodsItemSubDTO orderItemDTO1 = new GoodsItemSubDTO(orderId,1,10);
        GoodsItemSubDTO orderItemDTO2 = new GoodsItemSubDTO(orderId,2,20);

        List<GoodsItemSubDTO> goodsItemSubDTOS = new ArrayList<>();
        goodsItemSubDTOS.add(orderItemDTO1);
        goodsItemSubDTOS.add(orderItemDTO2);

        //使用redis+lua尝试扣减库存
        if(!checkGoodsStock(goodsItemSubDTOS)){
            mqMessageSender.sendGoodsStockNotEnoughMsg(orderId,goodsItemSubDTOS);
            throw new Exception("stock is not enough");
        }

        //订单消息录入到数据库，状态处理中
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);
        orderEntity.setCreateTime(LocalDateTime.now());
        orderEntity.setReason("");
        orderEntity.setStatus(0);
        orderEntity.setDetail(JSONUtil.toJsonPrettyStr(goodsItemSubDTOS));
        //orderMapper.insertWithPk(orderEntity);

        //发送创建订单消息
        mqMessageSender.sendCreateOrderMsg(orderId,goodsItemSubDTOS);
        return orderId;
    }


    private boolean checkGoodsStock(List<GoodsItemSubDTO> goodsItemSubDTOS) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript();
        redisScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource("/lua/stock_check.lua")));
        redisScript.setResultType(Long.class);


        List<String> keys = goodsItemSubDTOS.stream().map(goodsItemSubDTO -> goodsItemSubDTO.getGoodsId()+"").collect(Collectors.toList());
        Integer[] args = goodsItemSubDTOS.stream().map(goodsItemSubDTO -> goodsItemSubDTO.getQuantity()).collect(Collectors.toList()).toArray(new Integer[]{});
        Long result = (Long) redisTemplate.execute(redisScript,keys,args);
        return result>0;
    }

    /**
     * 订单状态变为1：成功
     * */
    @Override
    public void statusToSucces(Long orderId) {


        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);
        orderEntity.setStatus(1);
        //orderMapper.updateById(orderEntity);
    }

    /**
     * 订单状态变为2：失败
     * */
    @Override
    public void statusToFailed(Long orderId, String reason) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);
        orderEntity.setStatus(2);
        //orderMapper.updateById(orderEntity);
    }

    /**
     * 创建状态为未支付的订单，并发送延迟队列消息
     * */
    @Override
    public void createRandomTimeOrderWithoutPay() {

        //创建订单并插入到数据库
        long orderId = GuidUtil.longGuid();
        OrderEntity testOrder = new OrderEntity();
        testOrder.setOrderId(orderId);
        testOrder.setStatus(1);
        testOrder.setCreateTime(LocalDateTime.now());
        testOrder.setDetail(JSONUtil.toJsonPrettyStr(testOrder));
        //orderMapper.insert(testOrder); 这里是fluent ,大家可用自己的orm框架

        //发送延迟队列
        clusterServiceInvoker.orderInDelayQuene("mini-cloud-core-processer","default",orderId,1);

    }

    /**
     * 将消息添加到延迟队列的方法
     * */
    @Override
    public void pushOrderWithoutPayIndelayQuene(Long orderId, Long expireTime) {
        orderWithoutPayQuene.push(orderId,expireTime, TimeUnit.MINUTES);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.BASE)
    public String newOrder() throws Exception {

        log.info("开始全局事务，XID = " + RootContext.getXID());
         //demo 测试使用，实际应该用有意义的分布式id
        String orderId = System.nanoTime()+"";
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setOrderId(orderId);
        ordersEntity.setOrderCreateTime(LocalDateTime.now());
        ordersMapper.insertWithPk(ordersEntity);

        boolean result = false;//remoteSimulateGoodsService.subStock(1,3);
        if(result){
            log.info("创建订单成功 ,id:{}",orderId);
        }else {
            throw new Exception("库存不足，创建订单失败");
        }
        return orderId;
    }
}
