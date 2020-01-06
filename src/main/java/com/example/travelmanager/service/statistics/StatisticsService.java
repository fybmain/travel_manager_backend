package com.example.travelmanager.service.statistics;

import com.example.travelmanager.response.statistics.PayBudgetDiffDiagram;

import java.util.ArrayList;

public interface StatisticsService {
    void checkPermission(Integer userId, Integer departmentId);

    PayBudgetDiffDiagram payBudgetDiff(Integer departmentId, Integer year, ArrayList<Integer> months, String type);
}
