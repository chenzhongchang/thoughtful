package csj.thoughtful.vipconcurrent.threadpool;

import csj.thoughtful.vipconcurrent.tools.SleepTools;

//演示普通的死锁和解决
public class NormalDeadLock {

    private static Object valueFirst = new Object(); //第一个锁
    private static Object valueSecond = new Object(); //第二个锁

    //先拿第一个锁，再拿第二个锁
    private static void firstToSecond() throws InterruptedException {
        String threadName= Thread.currentThread().getName();
        synchronized (valueFirst){
            System.out.println(threadName+" get first");
            SleepTools.ms(100);
            synchronized (valueSecond){
                System.out.println(threadName+" get second");
            }
        }
    }

    //先拿第二个锁，再拿第一个锁
    private static void secondToFirst() throws InterruptedException {
        String threadName= Thread.currentThread().getName();
        synchronized (valueSecond){
            System.out.println(threadName+" get first");
            SleepTools.ms(100);
            synchronized (valueFirst){
                System.out.println(threadName+" get second");
            }
        }
    }

    //执行先拿第二个锁，再拿第一个锁
    private static class TestThread extends Thread{
        private String name;

        public TestThread(String name){
            this.name=name;
        }

        @Override
        public void run(){
            Thread.currentThread().setName(name);
            try {
                secondToFirst();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("TestDeadLock");
        TestThread testThread = new TestThread("SubTestThread");
        testThread.start();
        try {
            firstToSecond();//先拿第一个锁，再拿第二个锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 怀疑发送死锁；
     * 通过jps 查询应用的id
     * 再通过 jstackid 查看应用的锁的持有情况
     * 发送死锁时，获取锁的顺序不一致导致
     *
     * 检查死锁  cd C:\Program Files\Java\jdk1.8.0_201\bin
     * dir
     * jps -m  获取死锁id
     * jstack 10828
     *
     * 解决方案：保持拿锁的顺序
     * 7ke 36m
     * */
}
