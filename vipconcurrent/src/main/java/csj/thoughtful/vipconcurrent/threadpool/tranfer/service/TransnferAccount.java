package csj.thoughtful.vipconcurrent.threadpool.tranfer.service;

import csj.thoughtful.vipconcurrent.threadpool.tranfer.UserAccount;

//所谓的：动态顺序死锁
public class TransnferAccount implements ITransfer {
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
        synchronized(from){//锁转出
            System.out.println(Thread.currentThread().getName()+
                    " get"+from.getName());
            Thread.sleep(100);
            synchronized(to){//锁转入
                System.out.println(Thread.currentThread().getName()
                +" get"+to.getName());
                from.flyMoney(amount);
                to.addMoney(amount);
            }
        }
    }
}
