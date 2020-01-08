package com.example.travelmanager.response.statistics;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PayAndBudgetList {
    @Getter @Setter
    private List<Integer> Budget;
    @Getter @Setter
    private List<Integer> Payment;

    public PayAndBudgetList() {
        Budget = new ArrayList<Integer>();
        Payment = new ArrayList<Integer>();
    }
}
