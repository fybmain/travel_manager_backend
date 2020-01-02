package com.example.travelmanager.response.payment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class SimplePaymentListResponse {
    @Setter @Getter
    Integer total;

    @Setter @Getter
    ArrayList<SimplePayment> items;
}



