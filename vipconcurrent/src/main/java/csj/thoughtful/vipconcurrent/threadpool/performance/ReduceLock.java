package csj.thoughtful.vipconcurrent.threadpool.performance;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

//缩小锁的范围
public class ReduceLock {

    private Map<String, String> matchMap = new HashMap<>();

    public synchronized boolean isMatch(String name, String regexp){
        String key = "user."+name;
        String job = matchMap.get(key);
        if(job ==null){
            return false;
        }else {
            return Pattern.matches(regexp, job);//很耗时间
        }
    }
    public  boolean isMatchReduce(String name, String regexp){
        String key = "user."+name;
        String job;
        synchronized(this){
            job = matchMap.get(key);
        }
        //避免多余的缩减锁的范围
//        job = job+"s";//时间太短
//        synchronized(this){
//            job = matchMap.get(key);
//        }
        if(job ==null){
            return false;
        }else {
            return Pattern.matches(regexp, job);
        }
    }

}
