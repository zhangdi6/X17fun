package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/6/11.
      */

import java.io.Serializable;
import java.util.List;

public class TodayEntity implements Serializable{


    private static final long serialVersionUID = 2723629604379370007L;
    private List<TodayPbulishedListBean> todayPbulishedList;

    public List<TodayPbulishedListBean> getTodayPbulishedList() {
        return todayPbulishedList;
    }

    public void setTodayPbulishedList(List<TodayPbulishedListBean> todayPbulishedList) {
        this.todayPbulishedList = todayPbulishedList;
    }

    public static class TodayPbulishedListBean implements Serializable{

        private static final long serialVersionUID = -5233944564823781756L;
        /**
         * createtime : {"date":11,"day":4,"hours":10,"minutes":2,"month":5,"nanos":0,"seconds":18,"time":1591840938000,"timezoneOffset":-480,"year":120}
         * deliveryTime : {"date":11,"day":4,"hours":22,"minutes":30,"month":5,"nanos":0,"seconds":0,"time":1591885800000,"timezoneOffset":-480,"year":120}
         * gender : 1
         * bornAddress : 北京市 市辖区 朝阳区
         * publishedStatus : 2
         * nickName : 萨瓦迪卡
         * count : 1
         * portait : https://17fun.oss-cn-beijing.aliyuncs.com/portait/11922493f774400b81ba7395cee9ad12/compressedImg_1513671826079.jpg
         * userId : 11922493f774400b81ba7395cee9ad12
         * productName : 一包纸
         * deadLine : {"date":11,"day":4,"hours":22,"minutes":30,"month":5,"nanos":0,"seconds":0,"time":1591885800000,"timezoneOffset":-480,"year":120}
         * displayUrl : https://17fun.oss-cn-beijing.aliyuncs.com/published/11922493f774400b81ba7395cee9ad12/b5cd48988a954880b64091d87d11d241/159184085321711.png,https://17fun.oss-cn-beijing.aliyuncs.com/published/11922493f774400b81ba7395cee9ad12/b5cd48988a954880b64091d87d11d241/1591840853749487.png,https://17fun.oss-cn-beijing.aliyuncs.com/published/11922493f774400b81ba7395cee9ad12/b5cd48988a954880b64091d87d11d241/1591840854228778.png,https://17fun.oss-cn-beijing.aliyuncs.com/published/11922493f774400b81ba7395cee9ad12/b5cd48988a954880b64091d87d11d241/1591840854661500.png
         * myPublishedId : b5cd48988a954880b64091d87d11d241
         * colleage : null
         * starCount : 0
         * deliveryAddress : 青海省 海东地区 化隆回族自治县
         * isAppreciate : 1
         * price : 12
         * productTag : 这有啥狗屁故事
         * appreciateCount : 1
         * remarkCoun : 0
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
        private int starCount;
        private String deliveryAddress;
        private String isAppreciate;
        private float price;
        private float salePrice;
        private String productTag;
        private String foodId;
        private int appreciateCount;
        private int remarkCoun;
        private int saledCount;
        private int totalOrderCount;
        private String isFocus;
        private String userStatus;
        private String aeraLongtitude;
        private String aeraLatitude;
        private String deliveryAera;
        private boolean isLove ;


        public boolean isLove() {
            return isLove;
        }

        public void setLove(boolean love) {
            isLove = love;
        }

        public String getDeliveryAera() {
            return deliveryAera;
        }


        public String getUserStatus() {
            return userStatus;
        }

        public String getFoodId() {
            return foodId;
        }

        public void setFoodId(String foodId) {
            this.foodId = foodId;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getAeraLongtitude() {
            return aeraLongtitude;
        }

        public String getAeraLatitude() {
            return aeraLatitude;
        }

        public void setDeliveryAera(String deliveryAera) {
            this.deliveryAera = deliveryAera;
        }

        public void setAeraLongtitude(String aeraLongtitude) {
            this.aeraLongtitude = aeraLongtitude;
        }

        public void setAeraLatitude(String aeraLatitude) {
            this.aeraLatitude = aeraLatitude;
        }

        public String getIsFocus() {
            return isFocus;
        }

        public void setIsFocus(String isFocus) {
            this.isFocus = isFocus;
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


        public float getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(float salePrice) {
            this.salePrice = salePrice;
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

        public String getIsAppreciate() {
            return isAppreciate;
        }

        public void setIsAppreciate(String isAppreciate) {
            this.isAppreciate = isAppreciate;
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

        public int getTotalOrderCount() {
            return totalOrderCount;
        }

        public void setTotalOrderCount(int totalOrderCount) {
            this.totalOrderCount = totalOrderCount;
        }

        public static class CreatetimeBean implements Serializable{
            private static final long serialVersionUID = -8725742418868905116L;
            /**
             * date : 11
             * day : 4
             * hours : 10
             * minutes : 2
             * month : 5
             * nanos : 0
             * seconds : 18
             * time : 1591840938000
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
            private static final long serialVersionUID = 1809916997601470014L;
            /**
             * date : 11
             * day : 4
             * hours : 22
             * minutes : 30
             * month : 5
             * nanos : 0
             * seconds : 0
             * time : 1591885800000
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
            private static final long serialVersionUID = -382248229296953252L;
            /**
             * date : 11
             * day : 4
             * hours : 22
             * minutes : 30
             * month : 5
             * nanos : 0
             * seconds : 0
             * time : 1591885800000
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
