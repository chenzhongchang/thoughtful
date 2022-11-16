package csj.thoughtful.vipconcurrent.threadpool.tranfer.service;

import csj.thoughtful.vipconcurrent.threadpool.tranfer.UserAccount;

// 银行转账动作接口
public interface  ITransfer {
    // from 转出账户
    // amount 转账资金

    void transfer(UserAccount from, UserAccount to, int amount)
            throws InterruptedException;
}
