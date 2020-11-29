package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/6/10.
      */

import java.util.List;

public class UserPushListEntity {

    private List<UserPublishedListBean> userPublishedList;

    public List<UserPublishedListBean> getUserPublishedList() {
        return userPublishedList;
    }

    public void setUserPublishedList(List<UserPublishedListBean> userPublishedList) {
        this.userPublishedList = userPublishedList;
    }

    public static class UserPublishedListBean {
        /**
         * createtime : {"date":10,"day":3,"hours":12,"minutes":37,"month":5,"nanos":0,"seconds":10,"time":1591763830000,"timezoneOffset":-480,"year":120}
         * deliveryTime : {"date":10,"day":3,"hours":23,"minutes":30,"month":5,"nanos":0,"seconds":0,"time":1591803000000,"timezoneOffset":-480,"year":120}
         * publishedStatus : 2
         * count : 2
         * userId : a7bfc14b0285416a9752ff4541516d98
         * productName : 雷霆嘎巴
         * deadLine : {"date":10,"day":3,"hours":23,"minutes":30,"month":5,"nanos":0,"seconds":0,"time":1591803000000,"timezoneOffset":-480,"year":120}
         * displayUrl : https://17fun.oss-cn-beijing.aliyuncs.com/published/a7bfc14b0285416a9752ff4541516d98/dff2c7a3b2454bf1b49e2a6593c38f50/1591763804393541.png,https://17fun.oss-cn-beijing.aliyuncs.com/published/a7bfc14b0285416a9752ff4541516d98/dff2c7a3b2454bf1b49e2a6593c38f50/1591763804988103.jpg
         * myPublishedId : dff2c7a3b2454bf1b49e2a6593c38f50
         * starCount : 0
         * deliveryAddress : 上海市 市辖区 长宁区
         * price : 87.5
         * productTag : zbc无情哈了哨吧叶柯里
         * appreciateCount : 0
         * remarkCoun : 0
         * saledCount : 0
         */

        private CreatetimeBean createtime;
        private DeliveryTimeBean deliveryTime;
        private String publishedStatus;
        private int count;
        private String userId;
        private String productName;
        private DeadLineBean deadLine;
        private String displayUrl;
        private String myPublishedId;
        private int starCount;
        private String deliveryAddress;
        private String foodId;
        private float price;
        private String productTag;
        private int appreciateCount;
        private int remarkCoun;
        private int saledCount;
        private boolean zan;
        private boolean love;
        private String isAppreciate;

        public String getIsAppreciate() {
            return isAppreciate;
        }

        public boolean isLove() {
            return love;
        }

        public boolean isZan() {
            return zan;
        }

        public void setLove(boolean love) {
            this.love = love;
        }

        public void setZan(boolean zan) {
            this.zan = zan;
        }

        public void setIsAppreciate(String isAppreciate) {
            this.isAppreciate = isAppreciate;
        }

        public CreatetimeBean getCreatetime() {
            return createtime;
        }

        public String getFoodId() {
            return foodId;
        }

        public void setFoodId(String foodId) {
            this.foodId = foodId;
        }

        public void setCreatetime(CreatetimeBean createtime) {
            this.createtime = createtime;
        }

        public DeliveryTimeBean getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(DeliveryTimeBean deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getPublishedStatus() {
            return publishedStatus;
        }

        public void setPublishedStatus(String publishedStatus) {
            this.publishedStatus = publishedStatus;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public DeadLineBean getDeadLine() {
            return deadLine;
        }

        public void setDeadLine(DeadLineBean deadLine) {
            this.deadLine = deadLine;
        }

        public String getDisplayUrl() {
            return displayUrl;
        }

        public void setDisplayUrl(String displayUrl) {
            this.displayUrl = displayUrl;
        }

        public String getMyPublishedId() {
            return myPublishedId;
        }

        public void setMyPublishedId(String myPublishedId) {
            this.myPublishedId = myPublishedId;
        }

        public int getStarCount() {
            return starCount;
        }

        public void setStarCount(int starCount) {
            this.starCount = starCount;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getProductTag() {
            return productTag;
        }

        public void setProductTag(String productTag) {
            this.productTag = productTag;
        }

        public int getAppreciateCount() {
            return appreciateCount;
        }

        public void setAppreciateCount(int appreciateCount) {
            this.appreciateCount = appreciateCount;
        }

        public int getRemarkCoun() {
            return remarkCoun;
        }

        public void setRemarkCoun(int remarkCoun) {
            this.remarkCoun = remarkCoun;
        }

        public int getSaledCount() {
            return saledCount;
        }

        public void setSaledCount(int saledCount) {
            this.saledCount = saledCount;
        }

        public static class CreatetimeBean {
            /**
             * date : 10
             * day : 3
             * hours : 12
             * minutes : 37
             * month : 5
             * nanos : 0
             * seconds : 10
             * time : 1591763830000
             * timezoneOffset : -480
             * year : 120
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
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

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
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

        public static class DeliveryTimeBean {
            /**
             * date : 10
             * day : 3
             * hours : 23
             * minutes : 30
             * month : 5
             * nanos : 0
             * seconds : 0
             * time : 1591803000000
             * timezoneOffset : -480
             * year : 120
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
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

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
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

        public static class DeadLineBean {
            /**
             * date : 10
             * day : 3
             * hours : 23
             * minutes : 30
             * month : 5
             * nanos : 0
             * seconds : 0
             * time : 1591803000000
             * timezoneOffset : -480
             * year : 120
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
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

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
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
