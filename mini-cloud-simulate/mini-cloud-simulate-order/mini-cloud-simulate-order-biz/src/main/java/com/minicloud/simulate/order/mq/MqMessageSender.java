package com.minicloud.simulate.order.mq;

import cn.hutool.json.JSONUtil;
import com.minicloud.goods.dto.GoodsItemSubDTO;
import com.minicloud.order.dto.DelayQueneOrderDTO;
import lombok.AllArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author alan.wang
 */
@Service
@AllArgsConstructor
public class MqMessageSender {

    public static final String TOPIC_CREATE_ORDER_DESTINATION = "topic-create-order";

    public static final String TOPIC_GOODS_STOCK_NOT_ENOUGH_DESTINATION = "topic-goods-stock-not-enough";

    public static final String TOPIC_ORDER_WITHOUT_PAY_DESTINATION = "topic-order-without-pay";


    private final RocketMQTemplate rocketMQTemplate;


    /**
     *  根据订单id和商品库存集合发送创建订单事务消息到rocketmq
     * */
    public void sendCreateOrderMsg(Long orderId,List<GoodsItemSubDTO> goodsItemSubDTOS) {

        MessageTransationDTO messageTransationDTO = new MessageTransationDTO();
        messageTransationDTO.setId(orderId+"");
        messageTransationDTO.setPayload("");
        messageTransationDTO.setType(MessageTransationDTO.CREATE_ORDER);
        Message<String> msg = MessageBuilder.withPayload(JSONUtil.toJsonStr(goodsItemSubDTOS)).build();
        rocketMQTemplate.sendMessageInTransaction(TOPIC_CREATE_ORDER_DESTINATION,msg,messageTransationDTO);
    }

    /**
     *  根据订单id和商品库存集合发送库存不足事务消息到rocketmq
     * */
    public void sendGoodsStockNotEnoughMsg(Long orderId,List<GoodsItemSubDTO> goodsItemSubDTOS) {

        MessageTransationDTO messageTransationDTO = new MessageTransationDTO();
        messageTransationDTO.setId(orderId+"");
        messageTransationDTO.setPayload("");
        messageTransationDTO.setType(MessageTransationDTO.GOODS_STOCK_NOT_ENOUGH);
        Message<String> msg = MessageBuilder.withPayload(JSONUtil.toJsonStr(goodsItemSubDTOS)).build();
        rocketMQTemplate.sendMessageInTransaction(TOPIC_GOODS_STOCK_NOT_ENOUGH_DESTINATION,msg,messageTransationDTO);
    }

    public void sendOrderWithoutPayInDelayQueneMsg(DelayQueneOrderDTO delayQueneOrderDTO) {

        Message<String> msg = MessageBuilder.withPayload(JSONUtil.toJsonStr(delayQueneOrderDTO)).build();
        rocketMQTemplate.syncSend(TOPIC_ORDER_WITHOUT_PAY_DESTINATION,msg,30000,3);
    }
}
