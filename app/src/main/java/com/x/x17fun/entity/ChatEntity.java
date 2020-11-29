package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/8/10.
      */

import java.util.List;

public class ChatEntity {

    private List<ChatRecordsBean> chatRecords;

    public List<ChatRecordsBean> getChatRecords() {
        return chatRecords;
    }

    public void setChatRecords(List<ChatRecordsBean> chatRecords) {
        this.chatRecords = chatRecords;
    }

    public static class ChatRecordsBean {
        /**
         * toUserNickAndPortait : null
         * createtime : 2020-08-10 15:18:32
         * fromUserId : a7bfc14b0285416a9752ff4541516d98
         * dataType : 1
         * fromUserNickAndPortait : 迪迪$$https://17fun.oss-cn-beijing.aliyuncs.com/portait/a7bfc14b0285416a9752ff4541516d98/1592915024479272.jpg
         * imRecordId : 0d5707dc5b964f0cbc65588bc75d04f9
         * relationId : null
         * toUserId : 56c9c724ce644efd8b3dbd3aa805061a
         * content : 哈哈哈特咳咳
         */

        private String toUserNickAndPortait;
        private String createtime;
        private String fromUserId;
        private String dataType;
        private String fromUserNickAndPortait;
        private String imRecordId;
        private String relationId;
        private String toUserId;
        private String content;
        private boolean hasTime;

        public String getToUserNickAndPortait() {
            return toUserNickAndPortait;
        }

        public boolean isHasTime() {
            return hasTime;
        }

        public void setHasTime(boolean hasTime) {
            this.hasTime = hasTime;
        }

        public void setToUserNickAndPortait(String toUserNickAndPortait) {
            this.toUserNickAndPortait = toUserNickAndPortait;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getFromUserNickAndPortait() {
            return fromUserNickAndPortait;
        }

        public void setFromUserNickAndPortait(String fromUserNickAndPortait) {
            this.fromUserNickAndPortait = fromUserNickAndPortait;
        }

        public String getImRecordId() {
            return imRecordId;
        }

        public void setImRecordId(String imRecordId) {
            this.imRecordId = imRecordId;
        }

        public String getRelationId() {
            return relationId;
        }

        public void setRelationId(String relationId) {
            this.relationId = relationId;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
