package com.example.travelmanager.service.statistics;

import com.example.travelmanager.response.statistics.PayBudgetDiffDiagram;
import com.example.travelmanager.response.statistics.PaymentVariationResponse;

import java.util.ArrayList;

public interface StatisticsService {
    void checkPermission(Integer userId, Integer departmentId);

    PayBudgetDiffDiagram payBudgetDiff(Integer departmentId, String startTime, String endTime);

    PaymentVariationResponse paymentVariation(Integer departmentId, String startTime, String endTime);
}
