package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/11/29.
      */

import java.util.List;

public class RemarkZoneEntity {

    private List<CommentListBean> commentList;

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public static class CommentListBean {
        /**
         * zoneUserId : bded737c53514c8d8b2082acd5df2d7b
         * userPortait : https://17fun.oss-cn-beijing.aliyuncs.com/portait/bded737c53514c8d8b2082acd5df2d7b/1606628243485146.png
         * createtime : 2020-11-29 15:48:45
         * remarkContent : 哈哈哈哈哈这家伙整的
         * publishedCommnetId : ea8d6322dab0422ea9c0351c7410a030
         * zoneId : f9d1da86e4e44aa7afde1cc231700c2f
         * userNickName : 黑怕
         * extraImg : null
         * userId : bded737c53514c8d8b2082acd5df2d7b
         */

        private String zoneUserId;
        private String userPortait;
        private String createtime;
        private String remarkContent;
        private String publishedCommnetId;
        private String zoneId;
        private String userNickName;
        private String extraImg;
        private String userId;

        public String getZoneUserId() {
            return zoneUserId;
        }

        public void setZoneUserId(String zoneUserId) {
            this.zoneUserId = zoneUserId;
        }

        public String getUserPortait() {
            return userPortait;
        }

        public void setUserPortait(String userPortait) {
            this.userPortait = userPortait;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getRemarkContent() {
            return remarkContent;
        }

        public void setRemarkContent(String remarkContent) {
            this.remarkContent = remarkContent;
        }

        public String getPublishedCommnetId() {
            return publishedCommnetId;
        }

        public void setPublishedCommnetId(String publishedCommnetId) {
            this.publishedCommnetId = publishedCommnetId;
        }

        public String getZoneId() {
            return zoneId;
        }

        public void setZoneId(String zoneId) {
            this.zoneId = zoneId;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getExtraImg() {
            return extraImg;
        }

        public void setExtraImg(String extraImg) {
            this.extraImg = extraImg;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
