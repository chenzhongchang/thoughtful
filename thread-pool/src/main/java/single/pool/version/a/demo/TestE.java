package single.pool.version.a.demo;

import single.pool.version.a.demo.user.UserInfo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TestE {

    public static void main(String[] args) {
        TestE testE=new TestE();
        testE.init();
    }

    public void init(){
        TestE.AdaptationConfig config=new TestE.AdaptationConfig();
        config.start();
    }

    private AtomicInteger num=new AtomicInteger(0);
    private AtomicInteger sum=new AtomicInteger(0);
    private volatile int b=2;

    public void increment(UserInfo user){
        int result=num.incrementAndGet();
        System.out.println("=====result:"+result);
        System.out.println(user.toString());

    }

    class AdaptationConfig extends Thread{
        @Override
        public void run() {
            ExecutorService newSingle = Executors.newSingleThreadExecutor();
            while (b>0){
                long startTime=System.currentTimeMillis();
                int leng=200;
                CountDownLatch countDownLatch=new CountDownLatch(leng);
                for(int i=0;i<leng;i++){
                    if(i==3){
                        countDownLatch.countDown();
                        continue;
                    }
                    newSingle.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int index=sum.incrementAndGet();
                                UserInfo user=new UserInfo(index);
                                increment(user);
                                System.out.println("====sum.incrementAndGet():" + index);
                                Thread.sleep(2000);
                            } catch (Exception e) {
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
