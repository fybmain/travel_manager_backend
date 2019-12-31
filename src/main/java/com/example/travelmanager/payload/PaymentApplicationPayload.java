package com.example.travelmanager.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class PaymentApplicationPayload {

    public class Payment {
        @Setter @Getter
        private Float hotel;
        @Setter @Getter
        private Float vehicle;
        @Setter @Getter
        private Float food;
        @Setter @Getter
        private Float other;
    }

    @Setter @Getter
    private Payment payment;

    @Setter @Getter
    private ArrayList<Integer> pictureIds;

    @Setter @Getter
    private Integer travelApplyId;

}

