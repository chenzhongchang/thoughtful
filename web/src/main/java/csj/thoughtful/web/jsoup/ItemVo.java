package csj.thoughtful.web.jsoup;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ItemVo<T> implements Delayed {
    private long activeTime;
    private T data;

    public ItemVo(long activeTime, T data){
        this.activeTime=TimeUnit.NANOSECONDS.convert(activeTime,TimeUnit.NANOSECONDS)+System.nanoTime();
        this.data=data;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public T getData() {
        return data;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long d = unit.convert(this.activeTime-System.nanoTime(),TimeUnit.NANOSECONDS);
        return d;
    }

    @Override
    public int compareTo(Delayed o) {
        long d=this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return d == 0L ? 0 : (d > 0L?1:-1);
    }
}