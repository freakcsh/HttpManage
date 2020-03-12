package com.freak.httpmanage.bean;

public class LoginOutEvent {
    private int code;

    public LoginOutEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LoginOutEvent{" +
                "code=" + code +
                '}';
    }
}
