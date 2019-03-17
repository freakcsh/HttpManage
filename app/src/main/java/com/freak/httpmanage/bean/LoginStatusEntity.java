package com.freak.httpmanage.bean;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2019/3/16
 */

public class LoginStatusEntity {

    /**
     * code : 200
     * profile : {"userId":1794699363,"nickname":"freak_csh","avatarUrl":"http://p3.music.126.net/ma8NC_MpYqC-dK_L81FWXQ==/109951163250233892.jpg","birthday":"-2209017600000","userType":0,"djStatus":0}
     * bindings : [{"expiresIn":2147483647,"expired":false,"tokenJsonStr":"{\"countrycode\":\"\",\"cellphone\":\"137****4100\",\"hasPassword\":true}","refreshTime":1552708437,"id":6825653043,"type":1,"userId":1794699363,"url":""}]
     */

    private int code;
    private ProfileBean profile;
    private List<BindingsBean> bindings;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }

    public List<BindingsBean> getBindings() {
        return bindings;
    }

    public void setBindings(List<BindingsBean> bindings) {
        this.bindings = bindings;
    }

    public static class ProfileBean {
        /**
         * userId : 1794699363
         * nickname : freak_csh
         * avatarUrl : http://p3.music.126.net/ma8NC_MpYqC-dK_L81FWXQ==/109951163250233892.jpg
         * birthday : -2209017600000
         * userType : 0
         * djStatus : 0
         */

        private int userId;
        private String nickname;
        private String avatarUrl;
        private String birthday;
        private int userType;
        private int djStatus;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public int getDjStatus() {
            return djStatus;
        }

        public void setDjStatus(int djStatus) {
            this.djStatus = djStatus;
        }

        @Override
        public String toString() {
            return "ProfileBean{" +
                    "userId=" + userId +
                    ", nickname='" + nickname + '\'' +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", userType=" + userType +
                    ", djStatus=" + djStatus +
                    '}';
        }
    }

    public static class BindingsBean {
        /**
         * expiresIn : 2147483647
         * expired : false
         * tokenJsonStr : {"countrycode":"","cellphone":"137****4100","hasPassword":true}
         * refreshTime : 1552708437
         * id : 6825653043
         * type : 1
         * userId : 1794699363
         * url :
         */

        private int expiresIn;
        private boolean expired;
        private String tokenJsonStr;
        private int refreshTime;
        private long id;
        private int type;
        private int userId;
        private String url;

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public boolean isExpired() {
            return expired;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }

        public String getTokenJsonStr() {
            return tokenJsonStr;
        }

        public void setTokenJsonStr(String tokenJsonStr) {
            this.tokenJsonStr = tokenJsonStr;
        }

        public int getRefreshTime() {
            return refreshTime;
        }

        public void setRefreshTime(int refreshTime) {
            this.refreshTime = refreshTime;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "BindingsBean{" +
                    "expiresIn=" + expiresIn +
                    ", expired=" + expired +
                    ", tokenJsonStr='" + tokenJsonStr + '\'' +
                    ", refreshTime=" + refreshTime +
                    ", id=" + id +
                    ", type=" + type +
                    ", userId=" + userId +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginStatusEntity{" +
                "code=" + code +
                ", profile=" + profile +
                ", bindings=" + bindings +
                '}';
    }
}
