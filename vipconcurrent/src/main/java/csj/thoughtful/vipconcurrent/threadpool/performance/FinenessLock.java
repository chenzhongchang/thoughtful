package csj.thoughtful.vipconcurrent.threadpool.performance;


import java.util.HashSet;
import java.util.Set;

// 减少锁的粒度
public class FinenessLock {

    public final Set<String> users = new HashSet<>();
    public final Set<String> queries = new HashSet<>();

    public void addUser(String u){
        synchronized (users){
            users.add(u);
        }
    }

    public void addQuery(String q){
        synchronized (users){
            queries.add(q);
        }
    }

    public void removeUser(String u){
        synchronized (users){//所谓减少锁的粒度
            users.remove(u);
        }
    }

//    public synchronized void removeQuery(String q){
//        queries.remove(q);
//    }

}