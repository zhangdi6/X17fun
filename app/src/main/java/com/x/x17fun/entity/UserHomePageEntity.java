package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/6/10.
      */

public class UserHomePageEntity {

    /**
     * userInfo : {"appreciateAmount":0,"birthday":{"date":14,"day":4,"hours":0,"minutes":0,"month":3,"seconds":0,"time":766252800000,"timezoneOffset":-480,"year":94},"bornAddress":"北京  崇文","coinAmount":0,"colleage":"北京邮电大学","company":"小米科技","createtime":{"date":2,"day":2,"hours":11,"minutes":46,"month":5,"seconds":18,"time":1591069578000,"timezoneOffset":-480,"year":120},"currentCity":"","fansCount":0,"focusCount":0,"frozenAmount":0,"frozenIncomeAmount":0,"funCode":"01202006021146189960","gender":"1","nickName":"迪迪","password":"true","payPassword":"false","phone":"17630360083","portait":"http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/portait%2Fa7bfc14b0285416a9752ff4541516d98%2F1591069597796135.jpg","profession":"总经理","signature":"杀马特洗剪吹","totalOrderCount":0,"userId":"a7bfc14b0285416a9752ff4541516d98","userStatus":"0"}
     * unDealOrderCount : 0
     */

    private UserInfoBean userInfo;
    private int unDealOrderCount;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public int getUnDealOrderCount() {
        return unDealOrderCount;
    }

    public void setUnDealOrderCount(int unDealOrderCount) {
        this.unDealOrderCount = unDealOrderCount;
    }

    public static class UserInfoBean {
        /**
         * appreciateAmount : 0
         * birthday : {"date":14,"day":4,"hours":0,"minutes":0,"month":3,"seconds":0,"time":766252800000,"timezoneOffset":-480,"year":94}
         * bornAddress : 北京  崇文
         * coinAmount : 0
         * colleage : 北京邮电大学
         * company : 小米科技
         * createtime : {"date":2,"day":2,"hours":11,"minutes":46,"month":5,"seconds":18,"time":1591069578000,"timezoneOffset":-480,"year":120}
         * currentCity :
         * fansCount : 0
         * focusCount : 0
         * frozenAmount : 0
         * frozenIncomeAmount : 0
         * funCode : 01202006021146189960
         * gender : 1
         * nickName : 迪迪
         * password : true
         * payPassword : false
         * phone : 17630360083
         * portait : http://edencity-cdn.oss-cn-qingdao.aliyuncs.com/portait%2Fa7bfc14b0285416a9752ff4541516d98%2F1591069597796135.jpg
         * profession : 总经理
         * signature : 杀马特洗剪吹
         * totalOrderCount : 0
         * userId : a7bfc14b0285416a9752ff4541516d98
         * userStatus : 0
         */

        private int appreciateAmount;
        private BirthdayBean birthday;
        private String bornAddress;
        private int coinAmount;
        private String colleage;
        private String company;
        private CreatetimeBean createtime;
        private String currentCity;
        private int fansCount;
        private int focusCount;
        private int frozenAmount;
        private int frozenIncomeAmount;
        private String funCode;
        private String gender;
        private String nickName;
        private String password;
        private String payPassword;
        private String phone;
        private String portait;
        private String isFocus;
        private String profession;
        private String signature;
        private int totalOrderCount;
        private String userId;
        private String userStatus;

        public String getIsFocus() {
            return isFocus;
        }

        public void setIsFocus(String isFocus) {
            this.isFocus = isFocus;
        }

        public int getAppreciateAmount() {
            return appreciateAmount;
        }

        public void setAppreciateAmount(int appreciateAmount) {
            this.appreciateAmount = appreciateAmount;
        }

        public BirthdayBean getBirthday() {
            return birthday;
        }

        public void setBirthday(BirthdayBean birthday) {
            this.birthday = birthday;
        }

        public String getBornAddress() {
            return bornAddress;
        }

        public void setBornAddress(String bornAddress) {
            this.bornAddress = bornAddress;
        }

        public int getCoinAmount() {
            return coinAmount;
        }

        public void setCoinAmount(int coinAmount) {
            this.coinAmount = coinAmount;
        }

        public String getColleage() {
            return colleage;
        }

        public void setColleage(String colleage) {
            this.colleage = colleage;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public CreatetimeBean getCreatetime() {
            return createtime;
        }

        public void setCreatetime(CreatetimeBean createtime) {
            this.createtime = createtime;
        }

        public String getCurrentCity() {
            return currentCity;
        }

        public void setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public int getFocusCount() {
            return focusCount;
        }

        public void setFocusCount(int focusCount) {
            this.focusCount = focusCount;
        }

        public int getFrozenAmount() {
            return frozenAmount;
        }

        public void setFrozenAmount(int frozenAmount) {
            this.frozenAmount = frozenAmount;
        }

        public int getFrozenIncomeAmount() {
            return frozenIncomeAmount;
        }

        public void setFrozenIncomeAmount(int frozenIncomeAmount) {
            this.frozenIncomeAmount = frozenIncomeAmount;
        }

        public String getFunCode() {
            return funCode;
        }

        public void setFunCode(String funCode) {
            this.funCode = funCode;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPayPassword() {
            return payPassword;
        }

        public void setPayPassword(String payPassword) {
            this.payPassword = payPassword;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPortait() {
            return portait;
        }

        public void setPortait(String portait) {
            this.portait = portait;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getTotalOrderCount() {
            return totalOrderCount;
        }

        public void setTotalOrderCount(int totalOrderCount) {
            this.totalOrderCount = totalOrderCount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public static class BirthdayBean {
            /**
             * date : 14
             * day : 4
             * hours : 0
             * minutes : 0
             * month : 3
             * seconds : 0
             * time : 766252800000
             * timezoneOffset : -480
             * year : 94
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

        public static class CreatetimeBean {
            /**
             * date : 2
             * day : 2
             * hours : 11
             * minutes : 46
             * month : 5
             * seconds : 18
             * time : 1591069578000
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
