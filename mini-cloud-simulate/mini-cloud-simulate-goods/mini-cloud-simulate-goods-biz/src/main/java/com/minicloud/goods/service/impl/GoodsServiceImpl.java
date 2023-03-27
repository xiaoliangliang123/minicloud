package com.minicloud.goods.service.impl;

import cn.org.atool.fluent.mybatis.base.crud.IQuery;
import cn.org.atool.fluent.mybatis.base.crud.IUpdate;
import com.minicloud.goods.dto.GoodsItemSubDTO;
import com.minicloud.goods.entity.GoodsEntity;
import com.minicloud.goods.entity.GoodsItemSubEntity;
import com.minicloud.goods.mapper.GoodsItemSubMapper;
import com.minicloud.goods.mapper.GoodsMapper;
import com.minicloud.goods.service.GoodsService;
import com.minicloud.goods.wrapper.GoodsItemSubQuery;
import com.minicloud.goods.wrapper.GoodsUpdate;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author alan.wang
 */
@Slf4j
@Service
@AllArgsConstructor
public class GoodsServiceImpl implements GoodsService {


    private final GoodsMapper goodsMapper;

    private final GoodsItemSubMapper goodsItemSubMapper;

    private final RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean subStock(Integer goodsId, Integer num) {


        log.info("开始全局事务，XID = " + RootContext.getXID());
        IUpdate iUpdate = new GoodsUpdate().set.goodsStock().applyFunc("goods_stock -"+num).end().where.goodsId().eq(goodsId).end();
        goodsMapper.updateBy(iUpdate);
        GoodsEntity goodsEntity = goodsMapper.findById(goodsId);
        if(goodsEntity.getGoodsStock()>0){
            log.info("库存扣除成功 ,goodsId:{},num:{}",goodsId,num);
            return true;
        }else {
            log.info("库存扣除失败 ,goodsId:{},num:{}",goodsId,num);
            return false;
        }
    }


    /**
     * 初始化商品库存demo,更新商品id为1和2的库存数量为1000,
     * 缓存更新为1000，为了方便redis用的key value模式，实际情况建议大家用hash 模式
     *
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStock() {

        Integer goods1Stock = 1000;
        Integer goods2Stock = 1000;
        GoodsEntity goodsEntity1 = new GoodsEntity();
        goodsEntity1.setGoodsId(1);
        goodsEntity1.setGoodsStock(goods1Stock);

        GoodsEntity goodsEntity2 = new GoodsEntity();
        goodsEntity2.setGoodsId(2);
        goodsEntity2.setGoodsStock(goods2Stock);

        goodsMapper.updateById(goodsEntity1);
        goodsMapper.updateById(goodsEntity2);
        redisTemplate.opsForValue().set("1", goods1Stock);
        redisTemplate.opsForValue().set("2", goods2Stock);
    }

    /**
     *
     *  扣减对应order的商品库存
     *  首先检查是否已经扣减过，如果已经扣减则直接返回成功
     *  如果没有扣减，则插入goodsItemSub扣减数据，并尝试扣减库存，库存不足则弹出库存不足提示
     *
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void itemSub(Long orderId,List<GoodsItemSubDTO> goodsItemSubDTOS) throws Exception {

        if (goodsItemSubMapper.count(new GoodsItemSubQuery().where.orderId().eq(orderId).end()) <=0)
        {
            for (GoodsItemSubDTO orderItemDTO : goodsItemSubDTOS) {
                GoodsItemSubEntity goodsItemSubEntity = new GoodsItemSubEntity();
                goodsItemSubEntity.setOrderId(orderItemDTO.getOrderId());
                goodsItemSubEntity.setGoodsId(orderItemDTO.getGoodsId());
                goodsItemSubEntity.setQuantity(orderItemDTO.getQuantity());
                goodsItemSubMapper.insertWithPk(goodsItemSubEntity);

                int updateCount = goodsMapper.updateBy(new GoodsUpdate().set.goodsStock().applyFunc("goods_stock -"+orderItemDTO.getQuantity()).end().where.goodsStock().ge(orderItemDTO.getQuantity()).and.goodsId().eq(orderItemDTO.getGoodsId()).end());
                if(updateCount<=0){
                    throw new Exception("goodsId :"+orderItemDTO.getGoodsId()+" stock not enough");
                }
            }
        }
    }


}
