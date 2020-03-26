package com.freak.httpmanage.mvpdagger.frame.mvp.model.entity;

public class DaggerLoginEntity {
    private String token;
    private String huanxin_id;
    private String huanxin_pwd;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHuanxin_id() {
        return huanxin_id;
    }

    public void setHuanxin_id(String huanxin_id) {
        this.huanxin_id = huanxin_id;
    }

    public String getHuanxin_pwd() {
        return huanxin_pwd;
    }

    public void setHuanxin_pwd(String huanxin_pwd) {
        this.huanxin_pwd = huanxin_pwd;
    }

    @Override
    public String toString() {
        return "DaggerLogingEntity{" +
                "token='" + token + '\'' +
                ", huanxin_id='" + huanxin_id + '\'' +
                ", huanxin_pwd='" + huanxin_pwd + '\'' +
                '}';
    }
}
