package com.example.travelmanager.service.auth;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

public class ApiToken {
    @Getter @Setter
    private Integer userId;

    @Getter @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date expire;

    public Boolean valid(){
        Date now = new Date();
        return expire.after(now);
    }
}