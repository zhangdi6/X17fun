package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/6/18.
      */

import java.io.Serializable;
import java.util.List;

public class BuyInOrderEntity implements Serializable {


    private static final long serialVersionUID = 2223320868779724645L;
    private List<PurchaseOrdersBean> purchaseOrders;

    public List<PurchaseOrdersBean> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrdersBean> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }


    public static class PurchaseOrdersBean implements Serializable{

        private static final long serialVersionUID = -661702832000557134L;
        /**
         * myPurchaseId : 4426b9252c9a4d74849f26cc70918e89
         * createtime : 2020:06:18 16:43:27
         * orderId : 01202006181643274652
         * purchaseCount : 1
         * receiverGender : 1
         * nickName : 萨瓦迪卡
         * orderNote : null
         * receiverName : 张迪
         * orderStatus : 0
         * portait : https://17fun.oss-cn-beijing.aliyuncs.com/portait/11922493f774400b81ba7395cee9ad12/compressedImg_1513671826079.jpg
         * userId : a7bfc14b0285416a9752ff4541516d98
         * publishedId : 713988616e7947ae87c857115219cffd
         * productName : 畅轻优酸乳
         * displayUrl : https://17fun.oss-cn-beijing.aliyuncs.com/published/11922493f774400b81ba7395cee9ad12/713988616e7947ae87c857115219cffd/1592452680250381.png,https://17fun.oss-cn-beijing.aliyuncs.com/published/11922493f774400b81ba7395cee9ad12/713988616e7947ae87c857115219cffd/1592452680698822.png,https://17fun.oss-cn-beijing.aliyuncs.com/published/11922493f774400b81ba7395cee9ad12/713988616e7947ae87c857115219cffd/1592452681117608.png,https://17fun.oss-cn-beijing.aliyuncs.com/published/11922493f774400b81ba7395cee9ad12/713988616e7947ae87c857115219cffd/1592452681639106.png,https://17fun.oss-cn-beijing.aliyuncs.com/published/11922493f774400b81ba7395cee9ad12/713988616e7947ae87c857115219cffd/1592452682071906.png
         * receiverAddress : 保利国际广场1008
         * receiverPhone : 17630360083
         * phone : 17744599364
         * providerId : 11922493f774400b81ba7395cee9ad12
         * payMethod : 1
         * totalCost : 18
         */

        private String myPurchaseId;
        private String createtime;
        private String orderId;
        private int purchaseCount;
        private String receiverGender;
        private String nickName;
        private String orderNote;
        private String receiverName;
        private String orderStatus;
        private String portait;
        private String userId;
        private String foodId;
        private String publishedId;
        private String productName;
        private String displayUrl;
        private String receiverAddress;
        private String receiverPhone;
        private String phone;
        private String providerId;
        private String payMethod;
        private float totalCost;
        private float totalSaleCost;

        @Override
        public String toString() {
            return "PurchaseOrdersBean{" +
                    "myPurchaseId='" + myPurchaseId + '\'' +
                    ", createtime='" + createtime + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", purchaseCount=" + purchaseCount +
                    ", receiverGender='" + receiverGender + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", orderNote='" + orderNote + '\'' +
                    ", receiverName='" + receiverName + '\'' +
                    ", orderStatus='" + orderStatus + '\'' +
                    ", portait='" + portait + '\'' +
                    ", userId='" + userId + '\'' +
                    ", publishedId='" + publishedId + '\'' +
                    ", productName='" + productName + '\'' +
                    ", displayUrl='" + displayUrl + '\'' +
                    ", receiverAddress='" + receiverAddress + '\'' +
                    ", receiverPhone='" + receiverPhone + '\'' +
                    ", phone='" + phone + '\'' +
                    ", providerId='" + providerId + '\'' +
                    ", payMethod='" + payMethod + '\'' +
                    ", totalCost=" + totalCost +
                    ", totalSaleCost=" + totalSaleCost +
                    '}';
        }

        public String getMyPurchaseId() {
            return myPurchaseId;
        }

        public void setMyPurchaseId(String myPurchaseId) {
            this.myPurchaseId = myPurchaseId;
        }

        public String getFoodId() {
            return foodId;
        }

        public void setFoodId(String foodId) {
            this.foodId = foodId;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public float getTotalSaleCost() {
            return totalSaleCost;
        }

        public void setTotalSaleCost(float totalSaleCost) {
            this.totalSaleCost = totalSaleCost;
        }

        public int getPurchaseCount() {
            return purchaseCount;
        }

        public void setPurchaseCount(int purchaseCount) {
            this.purchaseCount = purchaseCount;
        }

        public String getReceiverGender() {
            return receiverGender;
        }

        public void setReceiverGender(String receiverGender) {
            this.receiverGender = receiverGender;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getOrderNote() {
            return orderNote;
        }

        public void setOrderNote(String orderNote) {
            this.orderNote = orderNote;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPortait() {
            return portait;
        }

        public void setPortait(String portait) {
            this.portait = portait;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPublishedId() {
            return publishedId;
        }

        public void setPublishedId(String publishedId) {
            this.publishedId = publishedId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getDisplayUrl() {
            return displayUrl;
        }

        public void setDisplayUrl(String displayUrl) {
            this.displayUrl = displayUrl;
        }

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public float getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(float totalCost) {
            this.totalCost = totalCost;
        }
    }

    @Override
    public String toString() {
        return "BuyInOrderEntity{" +
                "purchaseOrders=" + purchaseOrders +
                '}';
    }
}
