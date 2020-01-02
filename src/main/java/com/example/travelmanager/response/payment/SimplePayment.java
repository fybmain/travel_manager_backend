package com.example.travelmanager.response.payment;

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
    private String applyTime;

    @Getter @Setter
    private Integer status;
}
