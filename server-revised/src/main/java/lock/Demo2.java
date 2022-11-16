package lock;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo2 {
	 
    private int queueSize = 10 ;//最大仓库容量
    private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);
 
    private Lock lock = new ReentrantLock();
    private Condition fullCondition = lock.newCondition();
    private Condition emptyCondition = lock.newCondition();
 
    class Consumer implements Runnable{
 
        @Override
        public void run() {
            consume();
        }
    
        //condition 必须在lock的保护之下
        private void consume() {
            while(true){
                lock.lock();
                try {
                	//循环中判断
                    while(queue.size() == 0){
                        try {
                            System.out.println("===========产品为空无法消费================");
                            emptyCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    //通知可以生产了(如果被阻塞挂起的话)
                    fullCondition.signal();
                    System.out.println("==============消费一个剩余==========="+queue.size());
                } finally{
                    lock.unlock();
                }
            }
 
        }
    }
   
    class Producer implements Runnable{
 
        @Override
        public void run() {
            produce();
        }
 
        private void produce() {
            while(true){
                lock.lock();
                try {
                    while(queue.size()== queueSize){
                        try {
                            System.out.println("================商品已经满仓了，不能生产=============");
                            fullCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //生产一个
                    queue.offer(1);
                    System.out.println("==============生产一个剩余==========="+queue.size());
                    //通知不为空了
                    emptyCondition.signal();
                } finally{
                    lock.unlock();
                }
            }
        }
 
    }
     
    public static void main(String[] args) {
    	Demo2 demo = new Demo2();
        Consumer cus = demo.new Consumer();
        Producer pro = demo.new Producer();
        Producer pro2 = demo.new Producer();
        Thread thread1 = new Thread(cus);
        Thread thread2 = new Thread(pro);
         
        thread1.start();
        thread2.start();
    }
}

