package com.qf.damobobo.bean;

import java.util.List;

/**
 * Created by TimiZhuo on 2016/12/9.
 */
public class MyGuSshiData {

    /**
     * result : 1
     * msg : 获取数据成功
     * data : [{"id":"104","story_time":"1440729655","story_info":"倾听你们的故事","pics":["20150828/55dfca3787732.png","20150828/55dfca3787f3f.png","20150828/55dfca37885a1.png"],"uid":"1","lng":"18.00","lat":"19.00","city":"北京","readcount":"0","comment":"0"},{"id":"91","story_time":"1440718107","story_info":"早上好！！！","pics":null,"uid":"1","lng":null,"lat":null,"city":null,"readcount":"0","comment":"0"}]
     * user : {"id":"1","username":"kooeasy","usersex":"0","useremail":"koo@sina.com","nickname":"饭否否","birthday":"2012-06-06","portrait":"20150827/55dde54b8cfcd.png","signature":"反反复复"}
     */

    private int result;
    private String msg;
    private UserBean user;
    private List<DataBean> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class UserBean {
        /**
         * id : 1
         * username : kooeasy
         * usersex : 0
         * useremail : koo@sina.com
         * nickname : 饭否否
         * birthday : 2012-06-06
         * portrait : 20150827/55dde54b8cfcd.png
         * signature : 反反复复
         */

        private String id;
        private String username;
        private String usersex;
        private String useremail;
        private String nickname;
        private String birthday;
        private String portrait;
        private String signature;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsersex() {
            return usersex;
        }

        public void setUsersex(String usersex) {
            this.usersex = usersex;
        }

        public String getUseremail() {
            return useremail;
        }

        public void setUseremail(String useremail) {
            this.useremail = useremail;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }

    public static class DataBean {
        /**
         * id : 104
         * story_time : 1440729655
         * story_info : 倾听你们的故事
         * pics : ["20150828/55dfca3787732.png","20150828/55dfca3787f3f.png","20150828/55dfca37885a1.png"]
         * uid : 1
         * lng : 18.00
         * lat : 19.00
         * city : 北京
         * readcount : 0
         * comment : 0
         */

        private String id;
        private String story_time;
        private String story_info;
        private String uid;
        private String lng;
        private String lat;
        private String city;
        private String readcount;
        private String comment;
        private List<String> pics;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStory_time() {
            return story_time;
        }

        public void setStory_time(String story_time) {
            this.story_time = story_time;
        }

        public String getStory_info() {
            return story_info;
        }

        public void setStory_info(String story_info) {
            this.story_info = story_info;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getReadcount() {
            return readcount;
        }

        public void setReadcount(String readcount) {
            this.readcount = readcount;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }
}
