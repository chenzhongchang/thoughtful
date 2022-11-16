package csj.thoughtful.vipconcurrent.delayqueue.blockingqueue;

import java.util.concurrent.DelayQueue;

public class PutOrder implements Runnable{
    private DelayQueue<ItemVo<Order>> queue;

    public PutOrder(DelayQueue<ItemVo<Order>> queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        Order tb=new Order("Tb12345",366);
        ItemVo<Order> item=new ItemVo<>(5000,tb);
        this.queue.offer(item);
        System.out.println("订单5秒后到期：" + tb.getOrderNo());

        Order jd=new Order("Jd54321",366);
        ItemVo<Order> item1=new ItemVo<>(8000,jd);
        this.queue.offer(item1);
        System.out.println("订单8秒后到期：" + jd.getOrderNo());
    }
}
