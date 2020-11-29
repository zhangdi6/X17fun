package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/7/8.
      */

import java.util.List;

public class BillEntity {


    private List<AccountChangeRecordBean> accountChangeRecord;

    public List<AccountChangeRecordBean> getAccountChangeRecord() {
        return accountChangeRecord;
    }

    public void setAccountChangeRecord(List<AccountChangeRecordBean> accountChangeRecord) {
        this.accountChangeRecord = accountChangeRecord;
    }

    public static class AccountChangeRecordBean {
        /**
         * accountChangeRecordId : 2bd7998c2772476789d4b4e7b71f17b3
         * sourceRecordId : f5ee032222fe4e12a2caf5c49ed95f7a
         * changeSource : 2
         * createtime : 2020-07-08 15:36:07
         * changeType : 1
         * sourceRecordNo : 01202007081526042904
         * changeAmount : 4.5
         */

        private String accountChangeRecordId;
        private String sourceRecordId;
        private String changeSource;
        private String createtime;
        private String changeType;
        private String sourceRecordNo;
        private double changeAmount;

        public String getAccountChangeRecordId() {
            return accountChangeRecordId;
        }

        public void setAccountChangeRecordId(String accountChangeRecordId) {
            this.accountChangeRecordId = accountChangeRecordId;
        }

        public String getSourceRecordId() {
            return sourceRecordId;
        }

        public void setSourceRecordId(String sourceRecordId) {
            this.sourceRecordId = sourceRecordId;
        }

        public String getChangeSource() {
            return changeSource;
        }

        public void setChangeSource(String changeSource) {
            this.changeSource = changeSource;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getChangeType() {
            return changeType;
        }

        public void setChangeType(String changeType) {
            this.changeType = changeType;
        }

        public String getSourceRecordNo() {
            return sourceRecordNo;
        }

        public void setSourceRecordNo(String sourceRecordNo) {
            this.sourceRecordNo = sourceRecordNo;
        }

        public double getChangeAmount() {
            return changeAmount;
        }

        public void setChangeAmount(double changeAmount) {
            this.changeAmount = changeAmount;
        }
    }
}
