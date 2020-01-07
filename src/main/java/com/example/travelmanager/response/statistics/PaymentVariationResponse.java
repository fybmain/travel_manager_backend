package com.example.travelmanager.response.statistics;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PaymentVariationResponse {
    @Getter @Setter
    private List<MoneyDatePair> DataList;

    public PaymentVariationResponse() {
        DataList = new ArrayList<MoneyDatePair>();
    }
}
