package com.example.travelmanager.response.statistics;


import com.example.travelmanager.response.DoubleSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

public class MoneyDatePair {
    @JsonSerialize(using = DoubleSerialize.class)
    @Getter @Setter
    public Double money;
    @Getter @Setter
    public String dateString;

    public MoneyDatePair(Double money, String dateString) {
        this.money = money;
        this.dateString = dateString;
    }
}
