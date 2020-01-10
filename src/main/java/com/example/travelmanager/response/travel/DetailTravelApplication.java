package com.example.travelmanager.response.travel;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class DetailTravelApplication {
    @Getter @Setter
    private String applicantName;

    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private Integer applicantId;

    @Getter @Setter
    private Integer departmentId;

    @Getter @Setter
    private String departmentName;

    @Getter @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date applyTime;

    @Getter @Setter
    private Integer status;

    @Getter @Setter
    private Boolean paid;

    // 2019-01-01
    @Getter @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date startTime;

    @Getter @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date endTime;

    @Getter @Setter
    private String province;

    @Getter
    @Setter
    private String city;

    @Getter @Setter
    private String detailAddress;

    @Getter @Setter
    private String reason;

    @Getter @Setter
    private float hotelBudget;

    @Getter @Setter
    private float vehicleBudget;

    @Getter @Setter
    private float foodBudget;

    @Getter @Setter
    private float otherBudget;

    @Getter @Setter
    private String comment;
}
