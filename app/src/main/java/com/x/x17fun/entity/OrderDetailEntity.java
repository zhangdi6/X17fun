package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/6/18.
      */

public class OrderDetailEntity {

    /**
     * orderDetail : {"createtime":{"date":18,"day":4,"hours":16,"minutes":43,"month":5,"seconds":27,"time":1592469807425,"timezoneOffset":-480,"year":120},"myPurchaseId":"4426b9252c9a4d74849f26cc70918e89","orderId":"01202006181643274652","orderNote":"","orderStatus":"0","payMethod":"1","productName":"畅轻优酸乳","providerId":"11922493f774400b81ba7395cee9ad12","publishedId":"713988616e7947ae87c857115219cffd","purchaseCount":1,"receiverAddress":"保利国际广场1008","receiverGender":"1","receiverName":"张迪","receiverPhone":"17630360083","totalCost":18,"userId":"a7bfc14b0285416a9752ff4541516d98"}
     */

    private OrderDetailBean orderDetail;

    public OrderDetailBean getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailBean orderDetail) {
        this.orderDetail = orderDetail;
    }

    public static class OrderDetailBean {
        /**
         * createtime : {"date":18,"day":4,"hours":16,"minutes":43,"month":5,"seconds":27,"time":1592469807425,"timezoneOffset":-480,"year":120}
         * myPurchaseId : 4426b9252c9a4d74849f26cc70918e89
         * orderId : 01202006181643274652
         * orderNote :
         * orderStatus : 0
         * payMethod : 1
         * productName : 畅轻优酸乳
         * providerId : 11922493f774400b81ba7395cee9ad12
         * publishedId : 713988616e7947ae87c857115219cffd
         * purchaseCount : 1
         * receiverAddress : 保利国际广场1008
         * receiverGender : 1
         * receiverName : 张迪
         * receiverPhone : 17630360083
         * totalCost : 18
         * userId : a7bfc14b0285416a9752ff4541516d98
         */

        private CreatetimeBean createtime;
        private String myPurchaseId;
        private String orderId;
        private String orderNote;
        private String orderStatus;
        private String payMethod;
        private String productName;
        private String providerId;
        private String publishedId;
        private int purchaseCount;
        private String receiverAddress;
        private String receiverGender;
        private String receiverName;
        private String receiverPhone;
        private float totalCost;
        private float totalSaleCost;
        private String userId;

        public CreatetimeBean getCreatetime() {
            return createtime;
        }

        public void setCreatetime(CreatetimeBean createtime) {
            this.createtime = createtime;
        }

        public String getMyPurchaseId() {
            return myPurchaseId;
        }

        public void setMyPurchaseId(String myPurchaseId) {
            this.myPurchaseId = myPurchaseId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNote() {
            return orderNote;
        }

        public float getTotalSaleCost() {
            return totalSaleCost;
        }

        public void setTotalSaleCost(float totalSaleCost) {
            this.totalSaleCost = totalSaleCost;
        }

        public void setOrderNote(String orderNote) {
            this.orderNote = orderNote;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getPublishedId() {
            return publishedId;
        }

        public void setPublishedId(String publishedId) {
            this.publishedId = publishedId;
        }

        public int getPurchaseCount() {
            return purchaseCount;
        }

        public void setPurchaseCount(int purchaseCount) {
            this.purchaseCount = purchaseCount;
        }

        public String getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public String getReceiverGender() {
            return receiverGender;
        }

        public void setReceiverGender(String receiverGender) {
            this.receiverGender = receiverGender;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public float getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(float totalCost) {
            this.totalCost = totalCost;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public static class CreatetimeBean {
            /**
             * date : 18
             * day : 4
             * hours : 16
             * minutes : 43
             * month : 5
             * seconds : 27
             * time : 1592469807425
             * timezoneOffset : -480
             * year : 120
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }
}
