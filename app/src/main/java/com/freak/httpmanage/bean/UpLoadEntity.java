package com.freak.httpmanage.bean;

public class UpLoadEntity {
    private String avatar;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UpLoadEntity{" +
                "avatar='" + avatar + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
