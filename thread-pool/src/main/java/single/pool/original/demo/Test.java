package single.pool.original.demo;

import single.pool.original.executor.RosExecutor;

import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        String mac="cs";
        RosExecutor.execute(mac, new Runnable() {
            @Override
            public void run() {
                System.out.println("群雄逐鹿 问鼎");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
