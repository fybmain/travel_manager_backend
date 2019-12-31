package com.example.travelmanager.service.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

public class Token {
    private int id;

    private UserInfo userInfo;

    private String signature;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date expire;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public String getSignature() {
        return RandomStringUtils.randomAlphanumeric(500);
    }
}
