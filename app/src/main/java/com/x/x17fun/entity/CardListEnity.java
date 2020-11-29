package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/6/24.
      */

import java.util.List;

public class CardListEnity {

    private List<MyPayCardsBean> myPayCards;

    public List<MyPayCardsBean> getMyPayCards() {
        return myPayCards;
    }

    public void setMyPayCards(List<MyPayCardsBean> myPayCards) {
        this.myPayCards = myPayCards;
    }

    public static class MyPayCardsBean {
        /**
         * outerAccountType : 1
         * bank : null
         * accountName : 张迪
         * accountNo : zdzjbnb
         * outerAccountId : 4f20b03e0d544bccac7ce98f77fdc6b4
         * userId : a7bfc14b0285416a9752ff4541516d98
         */

        private String outerAccountType;
        private String bank;
        private String accountName;
        private String accountNo;
        private String outerAccountId;
        private String userId;

        public String getOuterAccountType() {
            return outerAccountType;
        }

        public void setOuterAccountType(String outerAccountType) {
            this.outerAccountType = outerAccountType;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
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

        public String getOuterAccountId() {
            return outerAccountId;
        }

        public void setOuterAccountId(String outerAccountId) {
            this.outerAccountId = outerAccountId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
