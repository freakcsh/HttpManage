package com.freak.httpmanage.bean;

/**
 * Created by Administrator on 2018/12/25.
 */

public class LoginBean {
  private String userName;
  private String pwd;
  private String abc;
  private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", abc='" + abc + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
