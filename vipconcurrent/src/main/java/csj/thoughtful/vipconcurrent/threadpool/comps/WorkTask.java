package csj.thoughtful.vipconcurrent.threadpool.comps;

import java.util.Random;
import java.util.concurrent.Callable;

public class WorkTask implements Callable<Integer> {
    private String name;
    public WorkTask(String name){
        this.name=name;
    }


    @Override
    public Integer call() throws Exception {
        int sleepTime = new Random().nextInt(1000);
        try {
            Thread.sleep(sleepTime);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return sleepTime;
    }
}
