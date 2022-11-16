package server.syn;

//同步锁的运用
public class Test {
    public static void main(String[] args) {
        Test t=new Test();
        t.planningTask();
        for (int i=0;i<10;i++){
            Th1 th1=new Th1(t);
            th1.start();
        }
    }

    static class Th1 extends Thread {
        Test test;

        Th1(Test t){
            test=t;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("========Th1===run");
            test.planningTask();
        }

        @Override
        public synchronized void start() {
            super.start();
            System.out.println("========Th1===start");

        }
    }

    private void planningTask(){
        synchronized(this){
          int num = business();
          if (num>100){
              num=business1(num);
              System.out.println("num1:"+num);
          }
          if (num>100){
              num=business1(num);
              System.out.println("num2:"+num);
          }
          if (num>100){
              num=business1(num);
              System.out.println("num3:"+num);
          }
        }
    }
    //业务
    private static int business(){
        try {
            Thread.sleep(3*1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return 105;
    }

    private static int business1(int num){
        try {
            Thread.sleep(3*1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return num-1;
    }

}
