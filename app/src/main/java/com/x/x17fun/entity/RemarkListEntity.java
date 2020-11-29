package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/7/28.
      */

import java.util.ArrayList;
import java.util.List;

public class RemarkListEntity {

    private ArrayList<CommentListBean> commentList;

    public ArrayList<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "RemarkListEntity{" +
                "commentList=" + commentList +
                '}';
    }

    public static class CommentListBean {
        /**
         * publisherId : a7bfc14b0285416a9752ff4541516d98
         * createtime : 2020-07-28 17:10:28
         * remarkContent : gainsXP晕头哦
         * starsCount : null
         * publishedCommnetId : 76d835ce7d744dd080722c20d44e96e6
         * extraImg : null
         * userId : a7bfc14b0285416a9752ff4541516d98
         * publishedId : bf6d11e1f58043a490f108fee70cff24
         */

        private String publisherId;
        private String createtime;
        private String remarkContent;
        private String starsCount;
        private String publishedCommnetId;
        private String extraImg;
        private String userId;
        private String publishedId;
        private String userPortait;
        private String isHidden;
        private String userNickName;

        public String getIsHidden() {
            return isHidden;
        }

        public void setIsHidden(String isHidden) {
            this.isHidden = isHidden;
        }

        public String getPublisherId() {
            return publisherId;
        }

        public void setPublisherId(String publisherId) {
            this.publisherId = publisherId;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public String getUserPortait() {
            return userPortait;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
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

        public String getStarsCount() {
            return starsCount;
        }

        public void setStarsCount(String starsCount) {
            this.starsCount = starsCount;
        }

        public String getPublishedCommnetId() {
            return publishedCommnetId;
        }

        public void setPublishedCommnetId(String publishedCommnetId) {
            this.publishedCommnetId = publishedCommnetId;
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

        public String getPublishedId() {
            return publishedId;
        }

        public void setPublishedId(String publishedId) {
            this.publishedId = publishedId;
        }

        @Override
        public String toString() {
            return "CommentListBean{" +
                    "publisherId='" + publisherId + '\'' +
                    ", createtime='" + createtime + '\'' +
                    ", remarkContent='" + remarkContent + '\'' +
                    ", starsCount='" + starsCount + '\'' +
                    ", publishedCommnetId='" + publishedCommnetId + '\'' +
                    ", extraImg='" + extraImg + '\'' +
                    ", userId='" + userId + '\'' +
                    ", publishedId='" + publishedId + '\'' +
                    ", userPortait='" + userPortait + '\'' +
                    ", isHidden='" + isHidden + '\'' +
                    ", userNickName='" + userNickName + '\'' +
                    '}';
        }
    }
}
