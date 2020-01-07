package com.example.travelmanager.service.statistics;

import com.example.travelmanager.response.statistics.DepartmentCost;
import com.example.travelmanager.response.statistics.PayBudgetDiffDiagram;
import com.example.travelmanager.response.statistics.PaymentVariationResponse;

import java.util.ArrayList;
import java.util.List;

public interface StatisticsService {
    void checkPermission(Integer userId, Integer departmentId);

    PayBudgetDiffDiagram payBudgetDiff(Integer departmentId, String startTime, String endTime);

    PaymentVariationResponse paymentVariation(Integer departmentId, String startTime, String endTime);

    List<DepartmentCost> getDepartmentCost(String startTime, String endTime);

}
