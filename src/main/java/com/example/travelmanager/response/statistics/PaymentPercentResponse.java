package com.example.travelmanager.response.statistics;

import com.example.travelmanager.response.DoubleSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

public class PaymentPercentResponse {

    @JsonSerialize(using = DoubleSerialize.class)
    @Getter @Setter
    private Double hotel;

    @JsonSerialize(using = DoubleSerialize.class)
    @Getter @Setter
    private Double food;

    @JsonSerialize(using = DoubleSerialize.class)
    @Getter @Setter
    private Double vehicle;

    @JsonSerialize(using = DoubleSerialize.class)
    @Getter @Setter
    private Double other;
}
