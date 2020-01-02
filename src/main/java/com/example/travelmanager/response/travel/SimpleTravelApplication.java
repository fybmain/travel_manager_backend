package com.example.travelmanager.response.travel;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class SimpleTravelApplication{
    @Getter @Setter
    private Integer applyId;

    @Getter @Setter
    private String applicantName;

    @Getter @Setter
    private Date applyTime;

    @Getter @Setter
    private Integer status;

    @Getter @Setter
    private String DepartmentName;
}
