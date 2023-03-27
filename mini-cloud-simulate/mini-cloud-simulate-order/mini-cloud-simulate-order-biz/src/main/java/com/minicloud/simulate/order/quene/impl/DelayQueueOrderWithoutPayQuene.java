package com.minicloud.simulate.order.quene.impl;


import com.minicloud.simulate.order.quene.OrderWithoutPayQuene;
import com.minicloud.simulate.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @Author alan.wang
 * DelayQueue 队列添加消息实现类
 */
@AllArgsConstructor
@Service("delayQueueOrderWithoutPayQuene")
public class DelayQueueOrderWithoutPayQuene implements OrderWithoutPayQuene {

    private static DelayQueue delayQueue = new DelayQueue();
    private static Executor executor = Executors.newSingleThreadExecutor();
    private final OrderService orderService;

    @Override
    public void push(long orderId, long expireTime,TimeUnit timeUnit) {

        //添加如延迟消息队列
        delayQueue.add(new OrderWithoutPayDelayed(orderId,expireTime,timeUnit));
    }

    @Override
    public void take()  {

        //启动一个线程消费消息队列
        executor.execute(()->{
            try {
                while (true) {


                    OrderWithoutPayDelayed orderWithoutPayDelayed = (OrderWithoutPayDelayed) delayQueue.take();
                    long orderId = orderWithoutPayDelayed.getOrderId();

                    //将对应订单状态从1：已创建变更为4：已取消
                    //orderService.updateStatusToCancel(orderId);
                    System.out.println("delayQuene take,orderId:" + orderId);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    class OrderWithoutPayDelayed implements Delayed{

        private long orderId ;
        private long expriedTime ;

        public OrderWithoutPayDelayed(long orderId,long expriedTime,TimeUnit timeUnit){

            this.expriedTime = TimeUnit.MILLISECONDS.convert(expriedTime,timeUnit)+System.currentTimeMillis();
            this.orderId = orderId;
        }

        public long getOrderId() {
            return orderId;
        }

        public long getExpriedTime() {
            return expriedTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {

            return expriedTime-System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {

            if(this ==o)
                return 0;
            OrderWithoutPayDelayed orderWithoutPayDelayed = (OrderWithoutPayDelayed)o;
            if(this.expriedTime >=orderWithoutPayDelayed.expriedTime)
                return 1;
            return -1;
        }
    }
}
