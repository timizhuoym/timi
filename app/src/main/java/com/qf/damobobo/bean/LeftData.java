package com.qf.damobobo.bean;

import java.util.List;

/**
 * Created by TimiZhuo on 2016/12/7.
 */
public class LeftData {

    /**
     * result : 1
     * msg : 获取数据成功
     * data : [{"id":"39","story_time":"1440668612","story_info":"ceshiceshi","pics":null,"uid":"29","lng":null,"lat":null,"city":null,"readcount":"46","comment":"0","user":{"id":"29","username":"zz","usersex":null,"useremail":null,"nickname":"zz","birthday":null,"portrait":null,"signature":null}},{"id":"4","story_time":"1440642626","story_info":"今天晚上吃火锅","pics":["20150827/55de764229a0b.png"],"uid":"1","lng":"","lat":"","city":"","readcount":"0","comment":"0","user":{"id":"1","username":"kooeasy","usersex":"0","useremail":"koo@sina.com","nickname":"饭否否","birthday":"2012-06-06","portrait":"20150827/55dde54b8cfcd.png","signature":"反反复复"}},{"id":"5","story_time":"1440642753","story_info":"端午过了，春节还远吗？","pics":["20150827/55de76c201363.png","20150827/55de76c201a1c.png","20150827/55de76c201fc8.png","20150827/55de76c20318d.png","20150827/55de76c203714.png"],"uid":"1","lng":"","lat":"","city":"","readcount":"0","comment":"0","user":{"id":"1","username":"kooeasy","usersex":"0","useremail":"koo@sina.com","nickname":"饭否否","birthday":"2012-06-06","portrait":"20150827/55dde54b8cfcd.png","signature":"反反复复"}}]
     */

    private int result;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 39
         * story_time : 1440668612
         * story_info : ceshiceshi
         * pics : null
         * uid : 29
         * lng : null
         * lat : null
         * city : null
         * readcount : 46
         * comment : 0
         * user : {"id":"29","username":"zz","usersex":null,"useremail":null,"nickname":"zz","birthday":null,"portrait":null,"signature":null}
         */

        private String id;
        private String story_time;
        private String story_info;
        private List<String> pics;
        private String uid;
        private Object lng;
        private Object lat;
        private Object city;
        private String readcount;
        private String comment;
        private UserBean user;

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

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Object getLng() {
            return lng;
        }

        public void setLng(Object lng) {
            this.lng = lng;
        }

        public Object getLat() {
            return lat;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 29
             * username : zz
             * usersex : null
             * useremail : null
             * nickname : zz
             * birthday : null
             * portrait : null
             * signature : null
             */

            private String id;
            private String username;
            private Object usersex;
            private Object useremail;
            private String nickname;
            private Object birthday;
            private Object portrait;
            private Object signature;

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

            public Object getUsersex() {
                return usersex;
            }

            public void setUsersex(Object usersex) {
                this.usersex = usersex;
            }

            public Object getUseremail() {
                return useremail;
            }

            public void setUseremail(Object useremail) {
                this.useremail = useremail;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public Object getPortrait() {
                return portrait;
            }

            public void setPortrait(Object portrait) {
                this.portrait = portrait;
            }

            public Object getSignature() {
                return signature;
            }

            public void setSignature(Object signature) {
                this.signature = signature;
            }
        }
    }
}
