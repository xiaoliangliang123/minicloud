package com.minicloud.simulate.order.config;

import com.minicloud.simulate.order.quene.OrderWithoutPayQuene;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author alan.wang
 * 启动时启动delayquene 并进行不断循环获取
 */
@Component
public class DelayQueueRunner implements ApplicationRunner {

    @Resource(name = "delayQueueOrderWithoutPayQuene")
    private OrderWithoutPayQuene orderWithoutPayQuene;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //启动delayquene 循环take()
        orderWithoutPayQuene.take();
    }
}
