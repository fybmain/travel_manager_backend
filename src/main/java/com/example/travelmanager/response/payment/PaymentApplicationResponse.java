package com.example.travelmanager.response.payment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentApplicationResponse {

    @Data
    public static class Payment {
        @Setter @Getter
        private Float hotel;
        @Setter @Getter
        private Float vehicle;
        @Setter @Getter
        private Float food;
        @Setter @Getter
        private Float other;
    }

    @Data
    public static class Budget {
        @Setter @Getter
        private Float hotel;
        @Setter @Getter
        private Float vehicle;
        @Setter @Getter
        private Float food;
        @Setter @Getter
        private Float other;
    }

    //申请人姓名
    @Setter @Getter
    private String applicant;

    @Setter @Getter
    private String pictureURLs;

    // 对应出差申请Id
    @Setter @Getter
    private Integer travelApplyId;

    @Setter @Getter
    private Budget budget;

    @Setter @Getter
    private Payment payment;
}
