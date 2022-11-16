package single.pool.version.a.demo;

import single.pool.version.a.executor.RosExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class TestC {

    public static void main(String[] args) {
        TestC testa=new TestC();
        testa.init();

    }
    public void init(){
        AdaptationConfig config=new AdaptationConfig();
        config.start();
    }

    private AtomicInteger num=new AtomicInteger(0);
    private AtomicInteger sum=new AtomicInteger(0);
    private volatile int b=2;

    public void increment(){
        int result=num.incrementAndGet();
        System.out.println("=====result:"+result);
    }


    // ----单例模式
    private TestC(){}

    private static class TestHolder{
        public static TestC pool = new TestC();
    }

    public static TestC getInstance(){
        return TestC.TestHolder.pool;
    }// ----单例模式


    class AdaptationConfig extends Thread{

        @Override
        public void run() {
            while (b>0){
                long startTime=System.currentTimeMillis();
                CountDownLatch countDownLatch=new CountDownLatch(1000);
                for(int i=0;i<1000;i++){
                    if(i==3)
                        continue;
                    RosExecutor.execute("dev" + i, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                increment();
                                System.out.println("====sum.incrementAndGet():"+sum.incrementAndGet());
                                Thread.sleep(2000);
                            }catch (InterruptedException e){
                                System.out.println("=========e:"+e.getMessage());
                            }finally{
                                countDownLatch.countDown();
                            }
                        }
                    });
                }
                try {
                    countDownLatch.await();
                    long endTime=System.currentTimeMillis();
                    System.out.println("===============================================耗时："+(endTime-startTime));
                    Thread.sleep(10*1000);
                    b--;
                }catch (InterruptedException e){
                    System.out.println("=========e:"+e.getMessage());
                }
            }


        }
    }

}
