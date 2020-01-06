package com.example.travelmanager.controller;


import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.statistics.PayBudgetDiffPayload;
import com.example.travelmanager.response.travel.ProvinceAndTimesResponse;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.service.statistics.StatisticsService;
import com.example.travelmanager.service.travel.TravelApplicationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    private AuthService authService;

    @Autowired
    private TravelApplicationService travelApplicationService;


    @ApiOperation(value = "获取某个部门或全部部门的实际支出与预算信息。startTime endTime")
    @PostMapping("/pay_budget_diff_diagram")
    @ResponseBody
    public ResponseEntity payBudgetDiffDiagram(@RequestHeader(Constant.HEADER_STRING) String auth,
                                               @RequestBody PayBudgetDiffPayload payload) {
        // departmentId = -1时返回全部的
        // 此接口只能被部门经理和总经理调用
        // 1. 验证token
        Integer userId = authService.authorize(auth, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        // 2. checkPermission
        statisticsService.checkPermission(userId, payload.getDepartmentId());

        // type: food, other, hotel, vehicle
        var result = statisticsService.payBudgetDiff(payload.getDepartmentId(), payload.getStartTime(), payload.getEndTime());

        return ResultBean.success(result);
    }

    @GetMapping(value="/statistic/location")
    @ApiOperation(value = "获取某个时间段每个省份和城市的出差次数，包括起止月份，月份格式： yyyy-MM 如：2020-01")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = ProvinceAndTimesResponse.class),
            @ApiResponse(code = 400, message = "{code=1004, msg='日期字符串格式错误，正确格式：yyyy-MM 如：2020-01'}", response = ResultBean.class)
    })
    public HttpEntity getLocationNum(
            @RequestHeader(Constant.HEADER_STRING) String auth,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @ApiParam(value = "-1 for all department") @RequestParam(defaultValue = "-1") Integer departmentId
    ) {
        int uid = authService.authorize(auth, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);
        List<ProvinceAndTimesResponse> provinceAndTimesResponses =
                travelApplicationService.getTravelTimes(uid, departmentId, startTime, endTime);
        return ResultBean.success(provinceAndTimesResponses);
    }

}
