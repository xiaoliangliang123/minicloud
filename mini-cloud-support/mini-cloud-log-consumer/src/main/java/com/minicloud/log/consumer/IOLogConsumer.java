package com.minicloud.log.consumer;

import cn.hutool.json.JSONUtil;
import com.minicloud.common.dto.IOLogRecordDTO;
import com.minicloud.common.log.config.SpringContextUtils;
import com.minicloud.log.dao.IOLogRecordDao;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(consumerGroup = "group-a", topic = "iolog",consumeMode = ConsumeMode.ORDERLY)
public class IOLogConsumer implements RocketMQListener<String> {

    @Autowired
    private IOLogRecordDao ioLogRecordDao;

    @Override
    public void onMessage(String message) {

        IOLogRecordDTO ioLogRecordDTO = JSONUtil.toBean(message,IOLogRecordDTO.class);
        ioLogRecordDao.insert(ioLogRecordDTO);
    }
}
