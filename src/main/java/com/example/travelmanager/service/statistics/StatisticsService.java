package com.example.travelmanager.service.statistics;

import com.example.travelmanager.response.statistics.*;

import java.util.List;

public interface StatisticsService {
    void checkPermission(Integer userId, Integer departmentId);

    PayBudgetDiffResponse payBudgetDiff(Integer departmentId, String startTime, String endTime);

    PaymentPercentResponse paymentPercent(Integer userId, String date);

    PaymentPercentResponse paymentPercentDepartment(String date, Integer departmentId);

    PaymentVariationResponse paymentVariation(Integer departmentId, String startTime, String endTime);

    List<DepartmentCost> getDepartmentCost(String startTime, String endTime);

}
