package com.example.travelmanager.service.statistics;

import com.alibaba.fastjson.JSON;
import com.example.travelmanager.config.exception.StatisticsControllerException;
import com.example.travelmanager.dao.DepartmentDao;
import com.example.travelmanager.dao.StatisticsDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.response.statistics.MoneyDatePair;
import com.example.travelmanager.response.statistics.PayBudgetDiffDiagram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsImpl implements StatisticsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private StatisticsDao statisticsDao;

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
    public PayBudgetDiffDiagram payBudgetDiff(Integer departmentId, Integer year, ArrayList<Integer> months, String type) {
        List<String> timeList = new ArrayList<String>();

        if(year < 2000 || year > 2020) {
            throw StatisticsControllerException.YearErrorException;
        }
        for(var m : months) {
            if(m <= 0 || m >= 13) {
                throw StatisticsControllerException.MonthErrorException;
            }
            String time = year.toString() + "-" + m.toString();
            timeList.add(time);
        }

        System.out.print(JSON.toJSONString(timeList));

        PayBudgetDiffDiagram diagram = new PayBudgetDiffDiagram();
        diagram.setDiagramData(new ArrayList<MoneyDatePair>());

        if(type != "all") {
            List<MoneyDatePair> dataList = statisticsDao.listFoodMoneyDateOfPayment(departmentId, type, "payment_application");
            for(String needTime : timeList) {
                for(MoneyDatePair data:dataList) {
                    if(data.dateString.equals(needTime)) {
                        diagram.getDiagramData().add(data);
                        System.out.print(JSON.toJSONString(diagram));
                    }
                }
            }
            return diagram;
        } else {
            // TODO : 将每个类别加在一起
            return diagram;
        }

    }
}
