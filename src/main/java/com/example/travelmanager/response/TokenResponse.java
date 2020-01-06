package com.example.travelmanager.response;

import com.example.travelmanager.service.auth.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class TokenResponse {
    @Getter @Setter
    private String token;

    @Getter @Setter
    private UserInfo userInfo;

    @Getter @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date expire;
}
