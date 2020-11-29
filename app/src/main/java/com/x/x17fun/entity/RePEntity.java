package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/9/14.
      */

public class RePEntity {

    /**
     * foodDetail : {"appreciateCount":1,"createtime":{"date":14,"day":1,"hours":11,"minutes":11,"month":8,"seconds":32,"time":1600053092000,"timezoneOffset":-480,"year":120},"displayUrl":"https://17fun.oss-cn-beijing.aliyuncs.com/published/8922c472cbe34d63ab23098cda7fbcfe/24a76e6973484fda95da4667f79733ec/160005304981666.jpg,https://17fun.oss-cn-beijing.aliyuncs.com/published/8922c472cbe34d63ab23098cda7fbcfe/24a76e6973484fda95da4667f79733ec/1600053049970831.jpg","foodName":"披萨饼","foodTag":"美味好吃不用等","myFoodId":"7d7ab5e8ded84365b8c0a939a1cf5cac","saleCount":0,"userId":"8922c472cbe34d63ab23098cda7fbcfe"}
     */

    private FoodDetailBean foodDetail;

    public FoodDetailBean getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(FoodDetailBean foodDetail) {
        this.foodDetail = foodDetail;
    }

    public static class FoodDetailBean {
        /**
         * appreciateCount : 1
         * createtime : {"date":14,"day":1,"hours":11,"minutes":11,"month":8,"seconds":32,"time":1600053092000,"timezoneOffset":-480,"year":120}
         * displayUrl : https://17fun.oss-cn-beijing.aliyuncs.com/published/8922c472cbe34d63ab23098cda7fbcfe/24a76e6973484fda95da4667f79733ec/160005304981666.jpg,https://17fun.oss-cn-beijing.aliyuncs.com/published/8922c472cbe34d63ab23098cda7fbcfe/24a76e6973484fda95da4667f79733ec/1600053049970831.jpg
         * foodName : 披萨饼
         * foodTag : 美味好吃不用等
         * myFoodId : 7d7ab5e8ded84365b8c0a939a1cf5cac
         * saleCount : 0
         * userId : 8922c472cbe34d63ab23098cda7fbcfe
         */

        private int appreciateCount;
        private CreatetimeBean createtime;
        private String displayUrl;
        private String foodName;
        private String foodTag;
        private String myFoodId;
        private int saleCount;
        private String userId;

        public int getAppreciateCount() {
            return appreciateCount;
        }

        public void setAppreciateCount(int appreciateCount) {
            this.appreciateCount = appreciateCount;
        }

        public CreatetimeBean getCreatetime() {
            return createtime;
        }

        public void setCreatetime(CreatetimeBean createtime) {
            this.createtime = createtime;
        }

        public String getDisplayUrl() {
            return displayUrl;
        }

        public void setDisplayUrl(String displayUrl) {
            this.displayUrl = displayUrl;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getFoodTag() {
            return foodTag;
        }

        public void setFoodTag(String foodTag) {
            this.foodTag = foodTag;
        }

        public String getMyFoodId() {
            return myFoodId;
        }

        public void setMyFoodId(String myFoodId) {
            this.myFoodId = myFoodId;
        }

        public int getSaleCount() {
            return saleCount;
        }

        public void setSaleCount(int saleCount) {
            this.saleCount = saleCount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public static class CreatetimeBean {
            /**
             * date : 14
             * day : 1
             * hours : 11
             * minutes : 11
             * month : 8
             * seconds : 32
             * time : 1600053092000
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
