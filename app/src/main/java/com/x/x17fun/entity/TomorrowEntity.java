package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/6/9.
      */

import java.io.Serializable;
import java.util.List;

public class TomorrowEntity implements Serializable{


    private static final long serialVersionUID = -2660848301656772801L;
    private List<TomorrowPublishedListBean> tomorrowPublishedList;

    public List<TomorrowPublishedListBean> getTomorrowPublishedList() {
        return tomorrowPublishedList;
    }

    public void setTomorrowPublishedList(List<TomorrowPublishedListBean> tomorrowPublishedList) {
        this.tomorrowPublishedList = tomorrowPublishedList;
    }

    public static class TomorrowPublishedListBean implements Serializable {

        private static final long serialVersionUID = -3530197533380888970L;
        /**
         * createtime : {"date":9,"day":2,"hours":11,"minutes":2,"month":5,"nanos":0,"seconds":4,"time":1591671724000,"timezoneOffset":-480,"year":120}
         * deliveryTime : {"date":10,"day":3,"hours":10,"minutes":30,"month":5,"nanos":0,"seconds":0,"time":1591756200000,"timezoneOffset":-480,"year":120}
         * gender : 1
         * bornAddress : null
         * publishedStatus : 2
         * nickName : 迪迪
         * count : 2
         * portait : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/portait%2Fa7bfc14b0285416a9752ff4541516d98%2F1591069597796135.jpg
         * userId : a7bfc14b0285416a9752ff4541516d98
         * productName : 麻婆豆腐
         * deadLine : {"date":10,"day":3,"hours":10,"minutes":30,"month":5,"nanos":0,"seconds":0,"time":1591756200000,"timezoneOffset":-480,"year":120}
         * displayUrl : https://17fun.oss-cn-beijing.aliyuncs.com/published/a7bfc14b0285416a9752ff4541516d98/cf2565a1ee964805a668266ec4021177/1591671699908123.png,https://17fun.oss-cn-beijing.aliyuncs.com/published/a7bfc14b0285416a9752ff4541516d98/cf2565a1ee964805a668266ec4021177/1591671700196497.png,
         * myPublishedId : cf2565a1ee964805a668266ec4021177
         * colleage : null
         * deliveryAddress : 北京市 市辖区 朝阳区
         * price : 48.5
         * productTag : 你吃吧我不吃我不想吃我说了不想吃就是不想吃
         * saledCount : 0
         * totalOrderCount : 0
         */

        private CreatetimeBean createtime;
        private DeliveryTimeBean deliveryTime;
        private String gender;
        private String bornAddress;
        private String publishedStatus;
        private String nickName;
        private int count;
        private String portait;
        private String userId;
        private String productName;
        private DeadLineBean deadLine;
        private String displayUrl;
        private String myPublishedId;
        private String colleage;
        private String deliveryAddress;
        private float price;
        private float salePrice;
        private String productTag;
        private int saledCount;
        private int totalOrderCount;
        private int appreciateCount;
        private int remarkCoun;
        private String isAppreciate;
        private int starCount;
        private String isFocus;
        private String aeraLongtitude;
        private String aeraLatitude;
        private String foodId;
        private String userStatus;
        private String deliveryAera;
        private boolean isLove;


        @Override
        public String toString() {
            return "TomorrowPublishedListBean{" +
                    "createtime=" + createtime +
                    ", deliveryTime=" + deliveryTime +
                    ", gender='" + gender + '\'' +
                    ", bornAddress='" + bornAddress + '\'' +
                    ", publishedStatus='" + publishedStatus + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", count=" + count +
                    ", portait='" + portait + '\'' +
                    ", userId='" + userId + '\'' +
                    ", productName='" + productName + '\'' +
                    ", deadLine=" + deadLine +
                    ", displayUrl='" + displayUrl + '\'' +
                    ", myPublishedId='" + myPublishedId + '\'' +
                    ", colleage='" + colleage + '\'' +
                    ", deliveryAddress='" + deliveryAddress + '\'' +
                    ", price=" + price +
                    ", productTag='" + productTag + '\'' +
                    ", saledCount=" + saledCount +
                    ", totalOrderCount=" + totalOrderCount +
                    ", appreciateCount=" + appreciateCount +
                    ", remarkCoun=" + remarkCoun +
                    ", isAppreciate='" + isAppreciate + '\'' +
                    ", starCount=" + starCount +
                    ", isFocus='" + isFocus + '\'' +
                    ", aeraLongtitude='" + aeraLongtitude + '\'' +
                    ", aeraLatitude='" + aeraLatitude + '\'' +
                    ", deliveryAera='" + deliveryAera + '\'' +
                    '}';
        }

        public String getAeraLatitude() {
            return aeraLatitude;
        }

        public void setAeraLatitude(String aeraLatitude) {
            this.aeraLatitude = aeraLatitude;
        }

        public void setAeraLongtitude(String aeraLongtitude) {
            this.aeraLongtitude = aeraLongtitude;
        }

        public boolean isLove() {
            return isLove;
        }
        public String getFoodId() {
            return foodId;
        }

        public void setFoodId(String foodId) {
            this.foodId = foodId;
        }
        public void setLove(boolean love) {
            isLove = love;
        }
        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }
        public void setDeliveryAera(String deliveryAera) {
            this.deliveryAera = deliveryAera;
        }

        public String getAeraLongtitude() {
            return aeraLongtitude;
        }

        public String getDeliveryAera() {
            return deliveryAera;
        }

        public String getIsFocus() {
            return isFocus;
        }

        public void setIsFocus(String isFocus) {
            this.isFocus = isFocus;
        }

        public String getIsAppreciate() {
            return isAppreciate;
        }

        public void setStarCount(int starCount) {
            this.starCount = starCount;
        }

        public int getStarCount() {
            return starCount;
        }

        public void setIsAppreciate(String isAppreciate) {
            this.isAppreciate = isAppreciate;
        }

        public int getAppreciateCount() {
            return appreciateCount;
        }

        public int getRemarkCoun() {
            return remarkCoun;
        }

        public void setRemarkCoun(int remarkCoun) {
            this.remarkCoun = remarkCoun;
        }

        public void setAppreciateCount(int appreciateCount) {
            this.appreciateCount = appreciateCount;
        }

        public float getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(float salePrice) {
            this.salePrice = salePrice;
        }

        public CreatetimeBean getCreatetime() {
            return createtime;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBornAddress() {
            return bornAddress;
        }

        public void setBornAddress(String bornAddress) {
            this.bornAddress = bornAddress;
        }

        public String getPublishedStatus() {
            return publishedStatus;
        }

        public void setPublishedStatus(String publishedStatus) {
            this.publishedStatus = publishedStatus;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
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

        public String getColleage() {
            return colleage;
        }

        public void setColleage(String colleage) {
            this.colleage = colleage;
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

        public int getTotalOrderCount() {
            return totalOrderCount;
        }

        public void setTotalOrderCount(int totalOrderCount) {
            this.totalOrderCount = totalOrderCount;
        }

        public static class CreatetimeBean implements Serializable{
            private static final long serialVersionUID = 5081640391222366725L;
            /**
             * date : 9
             * day : 2
             * hours : 11
             * minutes : 2
             * month : 5
             * nanos : 0
             * seconds : 4
             * time : 1591671724000
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

        public static class DeliveryTimeBean implements Serializable {
            private static final long serialVersionUID = -7752074386210330817L;
            /**
             * date : 10
             * day : 3
             * hours : 10
             * minutes : 30
             * month : 5
             * nanos : 0
             * seconds : 0
             * time : 1591756200000
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
            private static final long serialVersionUID = 5027762504910466387L;
            /**
             * date : 10
             * day : 3
             * hours : 10
             * minutes : 30
             * month : 5
             * nanos : 0
             * seconds : 0
             * time : 1591756200000
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
