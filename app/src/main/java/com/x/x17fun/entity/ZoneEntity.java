package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/11/1.
      */

import java.util.List;

public class ZoneEntity {

    private List<ZoneListBean> zoneList;

    public List<ZoneListBean> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<ZoneListBean> zoneList) {
        this.zoneList = zoneList;
    }

    public static class ZoneListBean {
        /**
         * displayUrl : https://17fun.oss-cn-beijing.aliyuncs.com/zone/2df506db403a4c6d9376ca2a21ce8d33/timg.jpeg,https://17fun.oss-cn-beijing.aliyuncs.com/zone/2df506db403a4c6d9376ca2a21ce8d33/timg (1).jpeg
         * createtime : 2020-11-01 16:03:06
         * appreciateCount : 0
         * zoneId : 296ca174cb5a4df68994787725ae6daf
         * userId : 2df506db403a4c6d9376ca2a21ce8d33
         * nickNameAndPortait : 阿迪$$https://17fun.oss-cn-beijing.aliyuncs.com/portait/2df506db403a4c6d9376ca2a21ce8d33/uri_compat_temp_file.png
         * content : 我没有什么想说的
         * commentCount : 0
         */

        private String displayUrl;
        private String createtime;
        private int appreciateCount;
        private String zoneId;
        private String userId;
        private String nickNameAndPortait;
        private String content;
        private int commentCount;

        public String getDisplayUrl() {
            return displayUrl;
        }

        public void setDisplayUrl(String displayUrl) {
            this.displayUrl = displayUrl;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getAppreciateCount() {
            return appreciateCount;
        }

        public void setAppreciateCount(int appreciateCount) {
            this.appreciateCount = appreciateCount;
        }

        public String getZoneId() {
            return zoneId;
        }

        public void setZoneId(String zoneId) {
            this.zoneId = zoneId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickNameAndPortait() {
            return nickNameAndPortait;
        }

        public void setNickNameAndPortait(String nickNameAndPortait) {
            this.nickNameAndPortait = nickNameAndPortait;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }
    }
}
