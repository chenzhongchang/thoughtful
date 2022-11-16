package single.pool.version.a.demo;

import single.pool.version.a.executor.RosExecutor;

import java.util.concurrent.atomic.AtomicInteger;

public class TestB {

    public static void main(String[] args) {
        TestB testa=new TestB();
        testa.init();

    }
    public void init(){
        AdaptationConfig config=new AdaptationConfig();
        config.start();
    }

    private AtomicInteger num=new AtomicInteger(0);
    private AtomicInteger sum=new AtomicInteger(0);
    private volatile boolean b=true;

    public void increment(){
        int result=num.incrementAndGet();
        System.out.println("=====result:"+result);
    }


    // ----单例模式
    private TestB(){}

    private static class TestHolder{
        public static TestB pool = new TestB();
    }

    public static TestB getInstance(){
        return TestB.TestHolder.pool;
    }// ----单例模式


    class AdaptationConfig extends Thread{

        @Override
        public void run() {
            while (b){
                long startTime=System.currentTimeMillis();
                for(int i=0;i<2000;i++){
                    RosExecutor.execute("dev" + i, new Runnable() {
                        @Override
                        public void run() {
                            increment();

                            System.out.println("====sum.incrementAndGet():"+sum.incrementAndGet());
                            try {
                                Thread.sleep(200);
                                b=false;
                            }catch (InterruptedException e){
                                System.out.println("=========e:"+e.getMessage());
                            }
                        }
                    });
                }
                long endTime=System.currentTimeMillis();
                System.out.println("===============================================耗时："+(endTime-startTime));

                try {
                    Thread.sleep(10*1000);
                    b=false;
                }catch (InterruptedException e){
                    System.out.println("=========e:"+e.getMessage());
                }
            }


        }
    }

}
