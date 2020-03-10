package com.test.event;

/**
 * @ClassName PayEvent
 * @Description: TODO
 * @Author shibo
 * @Date 2020/3/10
 **/
public class PayEvent {
    /**
     * 账号
     */
    private String accountNo;
    /**
     * 变动金额
     */
    private double amount;
    /**
     * 事务号
     */
    private String txNo;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTxNo() {
        return txNo;
    }

    public void setTxNo(String txNo) {
        this.txNo = txNo;
    }
}
