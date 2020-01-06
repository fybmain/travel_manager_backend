package com.example.travelmanager.response.statistics;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PayBudgetDiffDiagram {
    @Setter @Getter
    private List<MoneyDatePair> FoodPaymentDataList;

    @Setter @Getter
    private List<MoneyDatePair> OtherPaymentDataList;

    @Setter @Getter
    private List<MoneyDatePair> VehiclePaymentDataList;

    @Setter @Getter
    private List<MoneyDatePair> AllPaymentDataList;

    @Setter @Getter
    private List<MoneyDatePair> HotelPaymentDataList;

    @Setter @Getter
    private List<MoneyDatePair> FoodBudgetDataList;

    @Setter @Getter
    private List<MoneyDatePair> OtherBudgetDataList;

    @Setter @Getter
    private List<MoneyDatePair> VehicleBudgetDataList;

    @Setter @Getter
    private List<MoneyDatePair> HotelBudgetDataList;

    @Setter @Getter
    private List<MoneyDatePair> AllBudgetDataList;

    public PayBudgetDiffDiagram() {
        FoodPaymentDataList = new ArrayList<MoneyDatePair>();
        OtherPaymentDataList = new ArrayList<MoneyDatePair>();
        VehiclePaymentDataList = new ArrayList<MoneyDatePair>();
        HotelPaymentDataList = new ArrayList<MoneyDatePair>();
        AllPaymentDataList = new ArrayList<MoneyDatePair>();

        FoodBudgetDataList = new ArrayList<MoneyDatePair>();
        OtherBudgetDataList = new ArrayList<MoneyDatePair>();
        VehicleBudgetDataList = new ArrayList<MoneyDatePair>();
        HotelBudgetDataList = new ArrayList<MoneyDatePair>();
        AllBudgetDataList = new ArrayList<MoneyDatePair>();
    }
}
