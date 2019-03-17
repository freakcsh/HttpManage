package com.freak.httpmanage.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/16.
 */

public class LoginEntity {

    /**
     * loginType : 1
     * code : 200
     * account : {"id":1794699363,"userName":"1_13790994100","type":1,"status":0,"whitelistAuthority":0,"createTime":1552619990230,"salt":"[B@32f340ee","tokenVersion":0,"ban":0,"baoyueVersion":0,"donateVersion":0,"vipType":0,"viptypeVersion":0,"anonimousUser":false}
     * profile : {"djStatus":0,"expertTags":null,"experts":{},"avatarImgId":109951163250233890,"backgroundImgId":109951162868126480,"backgroundUrl":"http://p1.music.126.net/_f8R60U9mZ42sSNvdPn2sQ==/109951162868126486.jpg","avatarImgIdStr":"109951163250233892","backgroundImgIdStr":"109951162868126486","userId":1794699363,"accountStatus":0,"vipType":0,"gender":0,"province":110000,"nickname":"freak_csh","userType":0,"mutual":false,"remarkName":null,"authStatus":0,"defaultAvatar":true,"avatarUrl":"http://p1.music.126.net/ma8NC_MpYqC-dK_L81FWXQ==/109951163250233892.jpg","birthday":-2209017600000,"city":110101,"detailDescription":"","followed":false,"description":"","signature":"","authority":0,"avatarImgId_str":"109951163250233892","followeds":0,"follows":3,"eventCount":0,"playlistCount":1,"playlistBeSubscribedCount":0}
     * bindings : [{"tokenJsonStr":"{\"countrycode\":\"\",\"cellphone\":\"13790994100\",\"hasPassword\":true}","expired":false,"userId":1794699363,"url":"","expiresIn":2147483647,"refreshTime":1552708437,"id":6825653043,"type":1}]
     */

    private int loginType;
    private int code;
    private String msg;
    private AccountBean account;
    private ProfileBean profile;
    private List<BindingsBean> bindings;

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
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

    public static class AccountBean {
        /**
         * id : 1794699363
         * userName : 1_13790994100
         * type : 1
         * status : 0
         * whitelistAuthority : 0
         * createTime : 1552619990230
         * salt : [B@32f340ee
         * tokenVersion : 0
         * ban : 0
         * baoyueVersion : 0
         * donateVersion : 0
         * vipType : 0
         * viptypeVersion : 0
         * anonimousUser : false
         */

        private int id;
        private String userName;
        private int type;
        private int status;
        private int whitelistAuthority;
        private long createTime;
        private String salt;
        private int tokenVersion;
        private int ban;
        private int baoyueVersion;
        private int donateVersion;
        private int vipType;
        private int viptypeVersion;
        private boolean anonimousUser;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getWhitelistAuthority() {
            return whitelistAuthority;
        }

        public void setWhitelistAuthority(int whitelistAuthority) {
            this.whitelistAuthority = whitelistAuthority;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public int getTokenVersion() {
            return tokenVersion;
        }

        public void setTokenVersion(int tokenVersion) {
            this.tokenVersion = tokenVersion;
        }

        public int getBan() {
            return ban;
        }

        public void setBan(int ban) {
            this.ban = ban;
        }

        public int getBaoyueVersion() {
            return baoyueVersion;
        }

        public void setBaoyueVersion(int baoyueVersion) {
            this.baoyueVersion = baoyueVersion;
        }

        public int getDonateVersion() {
            return donateVersion;
        }

        public void setDonateVersion(int donateVersion) {
            this.donateVersion = donateVersion;
        }

        public int getVipType() {
            return vipType;
        }

        public void setVipType(int vipType) {
            this.vipType = vipType;
        }

        public int getViptypeVersion() {
            return viptypeVersion;
        }

        public void setViptypeVersion(int viptypeVersion) {
            this.viptypeVersion = viptypeVersion;
        }

        public boolean isAnonimousUser() {
            return anonimousUser;
        }

        public void setAnonimousUser(boolean anonimousUser) {
            this.anonimousUser = anonimousUser;
        }

        @Override
        public String toString() {
            return "AccountBean{" +
                    "id=" + id +
                    ", userName='" + userName + '\'' +
                    ", type=" + type +
                    ", status=" + status +
                    ", whitelistAuthority=" + whitelistAuthority +
                    ", createTime=" + createTime +
                    ", salt='" + salt + '\'' +
                    ", tokenVersion=" + tokenVersion +
                    ", ban=" + ban +
                    ", baoyueVersion=" + baoyueVersion +
                    ", donateVersion=" + donateVersion +
                    ", vipType=" + vipType +
                    ", viptypeVersion=" + viptypeVersion +
                    ", anonimousUser=" + anonimousUser +
                    '}';
        }
    }

    public static class ProfileBean {
        /**
         * djStatus : 0
         * expertTags : null
         * experts : {}
         * avatarImgId : 109951163250233890
         * backgroundImgId : 109951162868126480
         * backgroundUrl : http://p1.music.126.net/_f8R60U9mZ42sSNvdPn2sQ==/109951162868126486.jpg
         * avatarImgIdStr : 109951163250233892
         * backgroundImgIdStr : 109951162868126486
         * userId : 1794699363
         * accountStatus : 0
         * vipType : 0
         * gender : 0
         * province : 110000
         * nickname : freak_csh
         * userType : 0
         * mutual : false
         * remarkName : null
         * authStatus : 0
         * defaultAvatar : true
         * avatarUrl : http://p1.music.126.net/ma8NC_MpYqC-dK_L81FWXQ==/109951163250233892.jpg
         * birthday : -2209017600000
         * city : 110101
         * detailDescription :
         * followed : false
         * description :
         * signature :
         * authority : 0
         * avatarImgId_str : 109951163250233892
         * followeds : 0
         * follows : 3
         * eventCount : 0
         * playlistCount : 1
         * playlistBeSubscribedCount : 0
         */

        private int djStatus;
        private Object expertTags;
        private ExpertsBean experts;
        private long avatarImgId;
        private long backgroundImgId;
        private String backgroundUrl;
        private String avatarImgIdStr;
        private String backgroundImgIdStr;
        private int userId;
        private int accountStatus;
        private int vipType;
        private int gender;
        private int province;
        private String nickname;
        private int userType;
        private boolean mutual;
        private Object remarkName;
        private int authStatus;
        private boolean defaultAvatar;
        private String avatarUrl;
        private long birthday;
        private int city;
        private String detailDescription;
        private boolean followed;
        private String description;
        private String signature;
        private int authority;
        private String avatarImgId_str;
        private int followeds;
        private int follows;
        private int eventCount;
        private int playlistCount;
        private int playlistBeSubscribedCount;

        public int getDjStatus() {
            return djStatus;
        }

        public void setDjStatus(int djStatus) {
            this.djStatus = djStatus;
        }

        public Object getExpertTags() {
            return expertTags;
        }

        public void setExpertTags(Object expertTags) {
            this.expertTags = expertTags;
        }

        public ExpertsBean getExperts() {
            return experts;
        }

        public void setExperts(ExpertsBean experts) {
            this.experts = experts;
        }

        public long getAvatarImgId() {
            return avatarImgId;
        }

        public void setAvatarImgId(long avatarImgId) {
            this.avatarImgId = avatarImgId;
        }

        public long getBackgroundImgId() {
            return backgroundImgId;
        }

        public void setBackgroundImgId(long backgroundImgId) {
            this.backgroundImgId = backgroundImgId;
        }

        public String getBackgroundUrl() {
            return backgroundUrl;
        }

        public void setBackgroundUrl(String backgroundUrl) {
            this.backgroundUrl = backgroundUrl;
        }

        public String getAvatarImgIdStr() {
            return avatarImgIdStr;
        }

        public void setAvatarImgIdStr(String avatarImgIdStr) {
            this.avatarImgIdStr = avatarImgIdStr;
        }

        public String getBackgroundImgIdStr() {
            return backgroundImgIdStr;
        }

        public void setBackgroundImgIdStr(String backgroundImgIdStr) {
            this.backgroundImgIdStr = backgroundImgIdStr;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getAccountStatus() {
            return accountStatus;
        }

        public void setAccountStatus(int accountStatus) {
            this.accountStatus = accountStatus;
        }

        public int getVipType() {
            return vipType;
        }

        public void setVipType(int vipType) {
            this.vipType = vipType;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public boolean isMutual() {
            return mutual;
        }

        public void setMutual(boolean mutual) {
            this.mutual = mutual;
        }

        public Object getRemarkName() {
            return remarkName;
        }

        public void setRemarkName(Object remarkName) {
            this.remarkName = remarkName;
        }

        public int getAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(int authStatus) {
            this.authStatus = authStatus;
        }

        public boolean isDefaultAvatar() {
            return defaultAvatar;
        }

        public void setDefaultAvatar(boolean defaultAvatar) {
            this.defaultAvatar = defaultAvatar;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public String getDetailDescription() {
            return detailDescription;
        }

        public void setDetailDescription(String detailDescription) {
            this.detailDescription = detailDescription;
        }

        public boolean isFollowed() {
            return followed;
        }

        public void setFollowed(boolean followed) {
            this.followed = followed;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getAuthority() {
            return authority;
        }

        public void setAuthority(int authority) {
            this.authority = authority;
        }

        public String getAvatarImgId_str() {
            return avatarImgId_str;
        }

        public void setAvatarImgId_str(String avatarImgId_str) {
            this.avatarImgId_str = avatarImgId_str;
        }

        public int getFolloweds() {
            return followeds;
        }

        public void setFolloweds(int followeds) {
            this.followeds = followeds;
        }

        public int getFollows() {
            return follows;
        }

        public void setFollows(int follows) {
            this.follows = follows;
        }

        public int getEventCount() {
            return eventCount;
        }

        public void setEventCount(int eventCount) {
            this.eventCount = eventCount;
        }

        public int getPlaylistCount() {
            return playlistCount;
        }

        public void setPlaylistCount(int playlistCount) {
            this.playlistCount = playlistCount;
        }

        public int getPlaylistBeSubscribedCount() {
            return playlistBeSubscribedCount;
        }

        public void setPlaylistBeSubscribedCount(int playlistBeSubscribedCount) {
            this.playlistBeSubscribedCount = playlistBeSubscribedCount;
        }

        public static class ExpertsBean {
        }

        @Override
        public String toString() {
            return "ProfileBean{" +
                    "djStatus=" + djStatus +
                    ", expertTags=" + expertTags +
                    ", experts=" + experts +
                    ", avatarImgId=" + avatarImgId +
                    ", backgroundImgId=" + backgroundImgId +
                    ", backgroundUrl='" + backgroundUrl + '\'' +
                    ", avatarImgIdStr='" + avatarImgIdStr + '\'' +
                    ", backgroundImgIdStr='" + backgroundImgIdStr + '\'' +
                    ", userId=" + userId +
                    ", accountStatus=" + accountStatus +
                    ", vipType=" + vipType +
                    ", gender=" + gender +
                    ", province=" + province +
                    ", nickname='" + nickname + '\'' +
                    ", userType=" + userType +
                    ", mutual=" + mutual +
                    ", remarkName=" + remarkName +
                    ", authStatus=" + authStatus +
                    ", defaultAvatar=" + defaultAvatar +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    ", birthday=" + birthday +
                    ", city=" + city +
                    ", detailDescription='" + detailDescription + '\'' +
                    ", followed=" + followed +
                    ", description='" + description + '\'' +
                    ", signature='" + signature + '\'' +
                    ", authority=" + authority +
                    ", avatarImgId_str='" + avatarImgId_str + '\'' +
                    ", followeds=" + followeds +
                    ", follows=" + follows +
                    ", eventCount=" + eventCount +
                    ", playlistCount=" + playlistCount +
                    ", playlistBeSubscribedCount=" + playlistBeSubscribedCount +
                    '}';
        }
    }

    public static class BindingsBean {
        /**
         * tokenJsonStr : {"countrycode":"","cellphone":"13790994100","hasPassword":true}
         * expired : false
         * userId : 1794699363
         * url :
         * expiresIn : 2147483647
         * refreshTime : 1552708437
         * id : 6825653043
         * type : 1
         */

        private String tokenJsonStr;
        private boolean expired;
        private int userId;
        private String url;
        private int expiresIn;
        private int refreshTime;
        private long id;
        private int type;

        public String getTokenJsonStr() {
            return tokenJsonStr;
        }

        public void setTokenJsonStr(String tokenJsonStr) {
            this.tokenJsonStr = tokenJsonStr;
        }

        public boolean isExpired() {
            return expired;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
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

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
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

        @Override
        public String toString() {
            return "BindingsBean{" +
                    "tokenJsonStr='" + tokenJsonStr + '\'' +
                    ", expired=" + expired +
                    ", userId=" + userId +
                    ", url='" + url + '\'' +
                    ", expiresIn=" + expiresIn +
                    ", refreshTime=" + refreshTime +
                    ", id=" + id +
                    ", type=" + type +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginEntity{" +
                "loginType=" + loginType +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", account=" + account +
                ", profile=" + profile +
                ", bindings=" + bindings +
                '}';
    }
}
