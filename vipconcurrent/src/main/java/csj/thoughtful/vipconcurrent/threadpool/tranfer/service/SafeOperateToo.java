package csj.thoughtful.vipconcurrent.threadpool.tranfer.service;

import csj.thoughtful.vipconcurrent.threadpool.tranfer.UserAccount;
import csj.thoughtful.vipconcurrent.tools.SleepTools;

import java.util.Random;

//不会产生死锁的安全转账第二种方法,尝试拿锁同样可以解决死锁
public class SafeOperateToo implements ITransfer {

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
        Random r = new Random();
        while (true){
            if(from.getLock().tryLock()){
                try {
                    if(to.getLock().tryLock()){
                        System.out.println(Thread.currentThread().getName()+
                                " get"+from.getName());
                        try {
                            System.out.println(Thread.currentThread().getName()+
                                    " get"+to.getName());
                            //进入这，两把锁都拿到了
                            from.flyMoney(amount);
                            to.addMoney(amount);
                            break;
                        }finally{
                            to.getLock().unlock();
                        }
                    }
                }finally {
                    from.getLock().unlock();
                }
            }
            SleepTools.ms(r.nextInt(10));//休眠10,为了错开拿锁的时间，防止出现活锁(两个锁互相谦让)
        }
    }
}
