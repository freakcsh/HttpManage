package com.freak.httpmanage.event;

/**
 * Created by Administrator on 2019/1/28.
 */

public class RxEvent {
    private String userName;
    private String passWord;
    private int code;

    public RxEvent(String userName, String passWord, int code) {
        this.userName = userName;
        this.passWord = passWord;
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "RxEvent{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", code=" + code +
                '}';
    }
}
