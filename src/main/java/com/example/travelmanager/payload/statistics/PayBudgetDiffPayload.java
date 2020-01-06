package com.example.travelmanager.payload.statistics;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class PayBudgetDiffPayload {
    @Setter @Getter
    private String startTime;

    @Setter @Getter
    private String endTime;

    @Setter @Getter
    private Integer departmentId;
}
