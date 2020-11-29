package com.x.x17fun.entity;

/* Created by AdScar
    on 2020/8/13.
      */

import java.io.Serializable;
import java.util.List;

public class UnReadListEntity implements Serializable {
    private static final long serialVersionUID = 4693455611487224510L;
    private List<UnReadRecordBean> unReadRecords;

    public List<UnReadRecordBean> getUnReadRecordsBean() {
        return unReadRecords;
    }

    public void setUnReadRecordsBean(List<UnReadRecordBean> myPayCards) {
        this.unReadRecords = myPayCards;
    }

    public static class UnReadRecordBean implements Serializable{


        private static final long serialVersionUID = 5495443888283765517L;
        private int unreadCounts;
        private String lasttime;
        private String fromUserId;
        private String nickName;
        private String readFlag;
        private String dataType;
        private String portait;
        private String content;

        public int getUnreadCounts() {
            return unreadCounts;
        }

        public void setUnreadCounts(int unreadCounts) {
            this.unreadCounts = unreadCounts;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }

        public String getFromUserId() {
            return fromUserId;
        }

        public void setFromUserId(String fromUserId) {
            this.fromUserId = fromUserId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getReadFlag() {
            return readFlag;
        }

        public void setReadFlag(String readFlag) {
            this.readFlag = readFlag;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getPortait() {
            return portait;
        }

        public void setPortait(String portait) {
            this.portait = portait;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
