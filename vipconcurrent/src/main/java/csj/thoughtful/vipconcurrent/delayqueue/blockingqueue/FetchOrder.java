package csj.thoughtful.vipconcurrent.delayqueue.blockingqueue;

import java.util.concurrent.DelayQueue;

public class FetchOrder implements Runnable{
    private DelayQueue<ItemVo<Order>> queue;

    public FetchOrder(DelayQueue<ItemVo<Order>> queue){this.queue=queue;}

    @Override
    public void run() {
        while(true) {
            try {
                ItemVo<Order> itemTb=this.queue.take();
                Order tb=itemTb.getData();
                System.out.println("get from queue:" + tb.getOrderNo());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
