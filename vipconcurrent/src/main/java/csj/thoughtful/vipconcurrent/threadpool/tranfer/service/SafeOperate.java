package csj.thoughtful.vipconcurrent.threadpool.tranfer.service;

import csj.thoughtful.vipconcurrent.threadpool.tranfer.UserAccount;

public class SafeOperate implements ITransfer {
    private static Object tieLock = new Object();//加时赛


    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
//        from.hashCode();//?无法保证hashcode是否被重写
        //identityHashCode返回最原始的HashCode

        int fromHash = System.identityHashCode(from);
        int toHash=System.identityHashCode(to);//1000万分之一
        //如果有ID,保持唯一，可以使用id
        //先锁hash小的那个
        if(fromHash<toHash){
            synchronized (from){
                System.out.println(Thread.currentThread().getName()+
                        " get"+to.getName());
                Thread.sleep(100);
                synchronized (to){
                    System.out.println(Thread.currentThread().getName()+
                            " get"+to.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }
        }else if(toHash<fromHash){
            synchronized (to){
                System.out.println(Thread.currentThread().getName()+
                        " get"+to.getName());
                Thread.sleep(100);
                synchronized (from){
                    System.out.println(Thread.currentThread().getName()+
                            " get"+from.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }
        }else{//解决hash冲突的方法
            synchronized (tieLock){
                synchronized (from){
                    synchronized (to){
                        from.flyMoney(amount);
                        to.addMoney(amount);
                    }
                }
            }
        }
    }
}
