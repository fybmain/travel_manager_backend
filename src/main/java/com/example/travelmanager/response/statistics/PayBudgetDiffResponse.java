package com.example.travelmanager.response.statistics;

/*

{
    food:{
        "budget": [1,2,3,4,5,6,7,8,9,10,11,12],
        "payment": [1,2,3,4,5,6,7,8,9,10,11,12],
    },
    "vehicle": {
        ""
    },
    "hotel": {

    },
    "other": {

    },
    "all": {

    }
}                       ]
* */

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PayBudgetDiffResponse {
    @Getter @Setter
    private PayAndBudgetList Food;

    @Getter @Setter
    private PayAndBudgetList Vehicle;

    @Getter @Setter
    private PayAndBudgetList Hotel;

    @Getter @Setter
    private PayAndBudgetList Other;

    @Getter @Setter
    private PayAndBudgetList All;

    public PayBudgetDiffResponse() {
        Food = new PayAndBudgetList();
        Vehicle = new PayAndBudgetList();
        Hotel = new PayAndBudgetList();
        Other = new PayAndBudgetList();
        All = new PayAndBudgetList();
    }
}
