package com.minicloud.simulate.order.quene.impl;

import com.minicloud.order.dto.DelayQueneOrderDTO;
import com.minicloud.simulate.order.mq.MqMessageSender;
import com.minicloud.simulate.order.quene.OrderWithoutPayQuene;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author alan.wang
 * mq 队列添加消息实现类
 */
@AllArgsConstructor
@Service("mqOrderWithoutPayQuene")
public class MQOrderWithoutPayQuene implements OrderWithoutPayQuene {

    private final MqMessageSender mqMessageSender;

    @Override
    public void push(long orderId, long expireTime, TimeUnit timeUnit) {
        DelayQueneOrderDTO delayQueneOrderDTO = new DelayQueneOrderDTO();
        delayQueneOrderDTO.setExpireTime(expireTime);
        delayQueneOrderDTO.setOrderId(orderId);
        mqMessageSender.sendOrderWithoutPayInDelayQueneMsg(delayQueneOrderDTO);
    }

    @Override
    public void take() {

    }
}
