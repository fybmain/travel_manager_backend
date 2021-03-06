package com.example.travelmanager.response.travel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class SimpleTravelApplication{
    @Getter @Setter
    private Integer applyId;

    @Getter @Setter
    private String applicantName;

    @Getter @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date applyTime;

    @Getter @Setter
    private Integer status;

    @Getter @Setter
    private String DepartmentName;
}
