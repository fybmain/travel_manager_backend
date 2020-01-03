package com.example.travelmanager.response.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

public class SimplePayment{

    // paymentApplication Id
    @Getter @Setter
    private Integer applyId;

    @Getter @Setter
    private String applicantName;

    @Getter @Setter
    private String departmentName;

    @Getter @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private String applyTime;

    @Getter @Setter
    private Integer status;
}
