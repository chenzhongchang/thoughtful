package csj.thoughtful.vipconcurrent.delayqueue.blockingqueue;

import java.util.concurrent.DelayQueue;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<ItemVo<Order>> queue = new DelayQueue<>();
        (new Thread(new PutOrder((queue)))).start();;
        (new Thread(new FetchOrder(queue))).start();

        for(int i=1;i<15;++i){
            Thread.sleep(500L);
            System.out.println(i*500);
        }
    }

}
