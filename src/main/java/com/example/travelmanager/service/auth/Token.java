package com.example.travelmanager.service.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

public class Token {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private UserInfo userInfo;

    @Setter
    private String signature;

    @Getter @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date expire;

    public String getSignature() {
        return RandomStringUtils.randomAlphanumeric(500);
    }
}
