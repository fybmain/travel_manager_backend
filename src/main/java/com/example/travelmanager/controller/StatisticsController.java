package com.example.travelmanager.controller;


import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.statistics.PayBudgetDiffPayload;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.service.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    private AuthService authService;


    @PostMapping("/pay_budget_diff_diagram")
    @ResponseBody
    public ResponseEntity payBudgetDiffDiagram(@RequestHeader(Constant.HEADER_STRING) String auth,
                                               @RequestBody PayBudgetDiffPayload payload) {
        // departmentId = -1时返回全部的
        // 此接口只能被部门经理和总经理调用
        // 1. 验证token
        // Integer userId = authService.authorize(auth, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        // 2. checkPermission
        // statisticsService.checkPermission(userId, payload.getDepartmentId());

        // type: food, other, hotel, vehicle
        var result = statisticsService.payBudgetDiff(payload.getDepartmentId(), payload.getYear(), payload.getMonths(), payload.getType());

        return ResultBean.success(result);
    }
}
