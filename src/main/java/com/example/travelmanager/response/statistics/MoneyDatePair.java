package com.example.travelmanager.response.statistics;


import lombok.Getter;
import lombok.Setter;

public class MoneyDatePair {
    @Getter @Setter
    public Double money;
    @Getter @Setter
    public String dateString;

    public MoneyDatePair(Double money, String dateString) {
        this.money = money;
        this.dateString = dateString;
    }
}
