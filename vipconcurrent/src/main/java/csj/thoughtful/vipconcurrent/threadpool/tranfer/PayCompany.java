package csj.thoughtful.vipconcurrent.threadpool.tranfer;

import csj.thoughtful.vipconcurrent.threadpool.tranfer.service.ITransfer;
import csj.thoughtful.vipconcurrent.threadpool.tranfer.service.SafeOperateToo;
import csj.thoughtful.vipconcurrent.threadpool.tranfer.service.TransnferAccount;

public class PayCompany {

    //执行转账动作的线程
    private static class TransferThread extends Thread{
        private String name;//线程名称
        private UserAccount from;
        private UserAccount to;
        private int amount;
        private ITransfer transfer; //实际的转账动作

        public TransferThread(String name, UserAccount from, UserAccount
                               to, int amount, ITransfer transfer){
            this.name=name;
            this.from=from;
            this.to=to;
            this.amount=amount;
            this.transfer=transfer;
        }

        @Override
        public void run(){
            Thread.currentThread().setName(name);
            try {
                transfer.transfer(from,to,amount);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        PayCompany payCompany = new PayCompany();
        UserAccount zhangsan=new UserAccount("zhangsan", 20000);
        UserAccount lisi=new UserAccount("lisi", 20000);
        ITransfer transfer = new SafeOperateToo();
//        ITransfer transfer = new TransnferAccount();
        TransferThread zhangsanToLisi =new TransferThread("zhangsanToLisi",zhangsan,lisi,2000,transfer);
        TransferThread lisiToZhangsan =new TransferThread("lisiToZhangsan",lisi,zhangsan,4000,transfer);
        zhangsanToLisi.start();
        lisiToZhangsan.start();
    }

}
