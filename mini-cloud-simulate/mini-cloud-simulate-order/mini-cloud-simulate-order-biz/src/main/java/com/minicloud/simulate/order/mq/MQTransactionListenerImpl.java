package com.minicloud.simulate.order.mq;

import cn.hutool.json.JSONUtil;
import com.minicloud.goods.dto.GoodsItemSubDTO;
import com.minicloud.simulate.order.mapper.OrderMapper;
import com.minicloud.simulate.order.wrapper.OrderQuery;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author alan.wang
 */
@RocketMQTransactionListener
public class MQTransactionListenerImpl implements RocketMQLocalTransactionListener {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        //如果是创建订单消息，则判断是否已经插入进数据库
        if(Objects.equals(arg,MessageTransationDTO.CREATE_ORDER)) {

            List<GoodsItemSubDTO> goodsItemSubDTOS = JSONUtil.toList(JSONUtil.parseArray(msg.getPayload()), GoodsItemSubDTO.class);
            Long orderId = goodsItemSubDTOS.stream().map(GoodsItemSubDTO::getOrderId).findFirst().get();
            if(orderMapper.count(new OrderQuery().where.orderId().eq(orderId).end())>0){
                return RocketMQLocalTransactionState.COMMIT;
            }else {
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        }
        return RocketMQLocalTransactionState.COMMIT;

    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        return RocketMQLocalTransactionState.COMMIT;
    }

}
