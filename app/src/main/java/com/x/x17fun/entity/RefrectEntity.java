package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/7/10.
      */

import java.util.List;

public class RefrectEntity {

    private List<WithdrawListBean> withdrawList;

    public List<WithdrawListBean> getWithdrawList() {
        return withdrawList;
    }

    public void setWithdrawList(List<WithdrawListBean> withdrawList) {
        this.withdrawList = withdrawList;
    }

    public static class WithdrawListBean {
        /**
         * bank : null
         * createtime : 2020-07-10 15:14:47
         * withdrawCode : 01202007101514479849
         * accountName : 张迪
         * accountNo : zdzjbnb
         * withdrawId : 3fff5a15d67a4ed8851f09540b919f48
         * withdrawTo : 3
         * withdrawAmount : 5
         * userId : a7bfc14b0285416a9752ff4541516d98
         * withdrawStatus : 1
         */

        private Object bank;
        private String createtime;
        private String withdrawCode;
        private String accountName;
        private String accountNo;
        private String withdrawId;
        private String withdrawTo;
        private int withdrawAmount;
        private String userId;
        private String withdrawStatus;

        public Object getBank() {
            return bank;
        }

        public void setBank(Object bank) {
            this.bank = bank;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getWithdrawCode() {
            return withdrawCode;
        }

        public void setWithdrawCode(String withdrawCode) {
            this.withdrawCode = withdrawCode;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getWithdrawId() {
            return withdrawId;
        }

        public void setWithdrawId(String withdrawId) {
            this.withdrawId = withdrawId;
        }

        public String getWithdrawTo() {
            return withdrawTo;
        }

        public void setWithdrawTo(String withdrawTo) {
            this.withdrawTo = withdrawTo;
        }

        public int getWithdrawAmount() {
            return withdrawAmount;
        }

        public void setWithdrawAmount(int withdrawAmount) {
            this.withdrawAmount = withdrawAmount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getWithdrawStatus() {
            return withdrawStatus;
        }

        public void setWithdrawStatus(String withdrawStatus) {
            this.withdrawStatus = withdrawStatus;
        }
    }
}
