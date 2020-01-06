package com.example.travelmanager.response.statistics;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PayBudgetDiffDiagram {
    @Setter @Getter
    private List<MoneyDatePair> DiagramData;
}
