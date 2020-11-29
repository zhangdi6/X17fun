package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/6/17.
      */

import java.util.List;

public class MyAddessEntity {


    private List<ReceiveListBean> receiveList;

    public List<ReceiveListBean> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(List<ReceiveListBean> receiveList) {
        this.receiveList = receiveList;
    }

    public static class ReceiveListBean {
        /**
         * receiverAddress : 一楼1008
         * receiverPhone : 17630360083
         * receiverAera : 合生麒麟新天地
         * receiverGender : 1
         * currentStatus : 0
         * receiverName : 张迪
         * aeraLongtitude : 116.48436240868843
         * aeraLatitude : 40.00295737724023
         * receiveTag : 家
         */

        private String receiverAddress;
        private String receiverPhone;
        private String receiverAera;
        private String receiverGender;
        private String currentStatus;
        private String receiverName;
        private String aeraLongtitude;
        private String aeraLatitude;
        private String receiveTag;

        public String getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public String getReceiverAera() {
            return receiverAera;
        }

        public void setReceiverAera(String receiverAera) {
            this.receiverAera = receiverAera;
        }

        public String getReceiverGender() {
            return receiverGender;
        }

        public void setReceiverGender(String receiverGender) {
            this.receiverGender = receiverGender;
        }

        public String getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(String currentStatus) {
            this.currentStatus = currentStatus;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getAeraLongtitude() {
            return aeraLongtitude;
        }

        public void setAeraLongtitude(String aeraLongtitude) {
            this.aeraLongtitude = aeraLongtitude;
        }

        public String getAeraLatitude() {
            return aeraLatitude;
        }

        public void setAeraLatitude(String aeraLatitude) {
            this.aeraLatitude = aeraLatitude;
        }

        public String getReceiveTag() {
            return receiveTag;
        }

        public void setReceiveTag(String receiveTag) {
            this.receiveTag = receiveTag;
        }
    }
}
