package com.example.travelmanager.service.statistics;

import com.example.travelmanager.config.exception.StatisticsControllerException;
import com.example.travelmanager.dao.DepartmentDao;
import com.example.travelmanager.dao.StatisticsRepo;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.response.statistics.MoneyDatePair;
import com.example.travelmanager.response.statistics.PayBudgetDiffDiagram;
import com.example.travelmanager.response.statistics.PaymentVariationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private StatisticsRepo statisticsRepo;

    @Override
    public void checkPermission(Integer userId, Integer departmentId) {
        var userQuery = userDao.findById(userId);
        if(userQuery.isEmpty()) {
            throw StatisticsControllerException.UserNotFoundException;
        }
        var user= userQuery.get();

        // 总经理可以无视department条件直接请求，这里只判断部门经理
        if(user.getRole() == UserRoleEnum.DepartmentManager.getRoleId()) {
            if(departmentId != -1) {
                var departmentQuery = departmentDao.findById(departmentId);
                if(departmentQuery.isEmpty()) {
                    throw StatisticsControllerException.DepartmentNotFoundException;
                }
                var department = departmentQuery.get();

                // 不是这个部门的部门经理
                if(!department.getManagerId().equals(user.getId())) {
                    throw StatisticsControllerException.PermissionDeniedException;
                }
            } else {
                // 部门经理不可以请求全部的
                throw StatisticsControllerException.PermissionDeniedException;
            }
        }

    }



    @Override
    public PayBudgetDiffDiagram payBudgetDiff(Integer departmentId, String startTime, String endTime) {

        PayBudgetDiffDiagram diagram = new PayBudgetDiffDiagram();

        // foodPayment
        List<MoneyDatePair> foodPaymentDataList = statisticsRepo.listOneMoneyDatePair(departmentId, "food_payment", "payment_application");
        for(MoneyDatePair pair:foodPaymentDataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                diagram.getFoodPaymentDataList().add(pair);
            }
        }

        List<MoneyDatePair> otherPaymentDataList = statisticsRepo.listOneMoneyDatePair(departmentId, "other_payment", "payment_application");
        for(MoneyDatePair pair:otherPaymentDataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                diagram.getOtherPaymentDataList().add(pair);
            }
        }

        List<MoneyDatePair> vehiclePaymentDataList = statisticsRepo.listOneMoneyDatePair(departmentId, "vehicle_payment", "payment_application");
        for(MoneyDatePair pair:vehiclePaymentDataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                diagram.getVehiclePaymentDataList().add(pair);
            }
        }

        List<MoneyDatePair> hotelPaymentDataList = statisticsRepo.listOneMoneyDatePair(departmentId, "hotel_payment", "payment_application");
        for (MoneyDatePair pair:hotelPaymentDataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                diagram.getHotelPaymentDataList().add(pair);
            }
        }

        List<MoneyDatePair> allPaymentDataList = statisticsRepo.listAllMoneyDatePair(departmentId, "payment_application");
        for(MoneyDatePair pair:allPaymentDataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                diagram.getAllPaymentDataList().add(pair);
            }
        }

        List<MoneyDatePair> foodBudgetDataList = statisticsRepo.listOneMoneyDatePair(departmentId, "food_budget", "travel_application");
        for (MoneyDatePair pair:foodBudgetDataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                diagram.getFoodBudgetDataList().add(pair);
            }
        }

        List<MoneyDatePair> hotelBudgetDataList = statisticsRepo.listOneMoneyDatePair(departmentId, "hotel_budget", "travel_application");
        for (MoneyDatePair pair:hotelBudgetDataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                diagram.getHotelBudgetDataList().add(pair);
            }
        }

        List<MoneyDatePair> vehicleBudgetDataList = statisticsRepo.listOneMoneyDatePair(departmentId, "vehicle_budget", "travel_application");
        for (MoneyDatePair pair:vehicleBudgetDataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                diagram.getVehicleBudgetDataList().add(pair);
            }
        }

        List<MoneyDatePair> otherBudgetDataList = statisticsRepo.listOneMoneyDatePair(departmentId, "other_budget", "travel_application");
        for (MoneyDatePair pair:otherBudgetDataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                diagram.getOtherBudgetDataList().add(pair);
            }
        }

        List<MoneyDatePair> allBudgetDataList = statisticsRepo.listAllMoneyDatePair(departmentId, "travel_application");
        for(MoneyDatePair pair:allBudgetDataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                diagram.getAllBudgetDataList().add(pair);
            }
        }

        return diagram;
    }

    @Override
    public PaymentVariationResponse paymentVariation(Integer departmentId, String startTime, String endTime) {
        PaymentVariationResponse response = new PaymentVariationResponse();

        List<MoneyDatePair> dataList = statisticsRepo.listAllMoneyDatePair(departmentId, "payment_application");
        for (MoneyDatePair pair:dataList) {
            if(timeCompare(pair.getDateString(), startTime, endTime)) {
                response.getDataList().add(pair);
            }
        }

        return response;
    }

    private Boolean timeCompare(String time, String startTime, String endTime) {
        return startTime.compareTo(time) <= 0 && endTime.compareTo(time) >= 0;
    }
}
