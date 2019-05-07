package com.freak.httpmanage.bean;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Optional;

/**
 * Created by Administrator on 2019/5/7.
 */

public class UserEntity {
    private String userName;
    private String password;
    private String position;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Optional<String> getPosition() {
        return Optional.ofNullable(position);
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public UserEntity(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserEntity() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
