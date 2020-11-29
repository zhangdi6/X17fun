package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/6/9.
      */

import java.io.Serializable;
import java.util.List;

public class MyPushedEntity implements Serializable {

    private static final long serialVersionUID = -26445137126948466L;
    private List<UserPublishedListBean> userPublishedList;

    public List<UserPublishedListBean> getUserPublishedList() {
        return userPublishedList;
    }

    public void setUserPublishedList(List<UserPublishedListBean> userPublishedList) {
        this.userPublishedList = userPublishedList;
    }

    public static class UserPublishedListBean implements Serializable{
        private static final long serialVersionUID = -4138562871506201583L;
        /**
         * createtime : {"date":9,"day":2,"hours":12,"minutes":12,"month":5,"nanos":0,"seconds":12,"time":1591675932000,"timezoneOffset":-480,"year":120}
         * deliveryTime : {"date":10,"day":3,"hours":12,"minutes":30,"month":5,"nanos":0,"seconds":0,"time":1591763400000,"timezoneOffset":-480,"year":120}
         * publishedStatus : 2
         * count : 3
         * userId : a7bfc14b0285416a9752ff4541516d98
         * productName : 麻婆豆腐
         * deadLine : {"date":10,"day":3,"hours":12,"minutes":30,"month":5,"nanos":0,"seconds":0,"time":1591763400000,"timezoneOffset":-480,"year":120}
         * displayUrl : https://17fun.oss-cn-beijing.aliyuncs.com/published/a7bfc14b0285416a9752ff4541516d98/fce44e27d71b4b1d917645ad4658a6cf/1591675914885739.png,https://17fun.oss-cn-beijing.aliyuncs.com/published/a7bfc14b0285416a9752ff4541516d98/fce44e27d71b4b1d917645ad4658a6cf/1591675915243247.png,
         * myPublishedId : fce44e27d71b4b1d917645ad4658a6cf
         * deliveryAddress : 北京市 市辖区 朝阳区
         * price : 51.5
         * productTag : 奥利给啊奥利给操
         * saledCount : 0
         */

        private CreatetimeBean createtime;
        private DeliveryTimeBean deliveryTime;
        private String publishedStatus;
        private int count;
        private String userId;
        private String foodId;
        private String productName;
        private DeadLineBean deadLine;
        private String displayUrl;
        private String myPublishedId;
        private String deliveryAddress;
        private String deliveryAera;
        private String aeraLongtitude;
        private String aeraLatitude;
        private float price;
        private String productTag;
        private int saledCount;


        public CreatetimeBean getCreatetime() {
            return createtime;
        }

        public String getDeliveryAera() {
            return deliveryAera;
        }

        public String getAeraLatitude() {
            return aeraLatitude;
        }

        public void setAeraLatitude(String aeraLatitude) {
            this.aeraLatitude = aeraLatitude;
        }

        public String getAeraLongtitude() {
            return aeraLongtitude;
        }

        public String getFoodId() {
            return foodId;
        }

        public void setFoodId(String foodId) {
            this.foodId = foodId;
        }

        public void setAeraLongtitude(String aeraLongtitude) {
            this.aeraLongtitude = aeraLongtitude;
        }

        public void setDeliveryAera(String deliveryAera) {
            this.deliveryAera = deliveryAera;
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

        public int getSaledCount() {
            return saledCount;
        }

        public void setSaledCount(int saledCount) {
            this.saledCount = saledCount;
        }

        public static class CreatetimeBean implements Serializable{
            private static final long serialVersionUID = 16590130707915221L;
            /**
             * date : 9
             * day : 2
             * hours : 12
             * minutes : 12
             * month : 5
             * nanos : 0
             * seconds : 12
             * time : 1591675932000
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

        public static class DeliveryTimeBean implements Serializable{
            private static final long serialVersionUID = 6443782500272656610L;
            /**
             * date : 10
             * day : 3
             * hours : 12
             * minutes : 30
             * month : 5
             * nanos : 0
             * seconds : 0
             * time : 1591763400000
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

        public static class DeadLineBean implements Serializable{
            private static final long serialVersionUID = 4258394554972738413L;
            /**
             * date : 10
             * day : 3
             * hours : 12
             * minutes : 30
             * month : 5
             * nanos : 0
             * seconds : 0
             * time : 1591763400000
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
