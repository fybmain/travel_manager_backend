package com.example.travelmanager.payload.statistics;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class PayBudgetDiffPayload {
    @Setter @Getter
    private Integer year;

    @Setter @Getter
    private ArrayList<Integer> months;

    @Setter @Getter
    private Integer departmentId;

    @Setter @Getter
    private String type;
}
