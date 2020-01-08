package com.example.travelmanager.controller;


import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.statistics.PayBudgetDiffPayload;
import com.example.travelmanager.payload.statistics.PaymentVariationPayload;
import com.example.travelmanager.response.statistics.*;
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

    // 图一
    @ApiOperation(value = "获取某个部门或全部部门的实际支出与预算信息。包括各项以及总共(all)的。 departmentId=-1时为全部门. startTime endTime 格式 2020-01")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=0, msg='' data如下}", response = PayBudgetDiffResponse.class),
            @ApiResponse(code = 500, message = "{code=2003 msg='后台内部sql中tablename错误（前端不用处理这个'}"),
            @ApiResponse(code = 403, message = "{code=1003, msg = \"user don't have enough permission to request this API\"}"),
            @ApiResponse(code = 404, message = "{code=1001 msg = \"request user not found in database\"}  {code=1002, msg= \"request department not found in database\"}")

    })
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

    // 图二个人各项费用比例

    @ApiOperation(value = "获取用户某个月的各项支出。time 格式 2020-01")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=0, msg='' data如下}", response = PaymentPercentResponse.class),
            @ApiResponse(code = 400, message = "{code=1004, msg='日期字符串格式错误，正确格式：yyyy-MM 如：2020-01'}", response = ResultBean.class),
            @ApiResponse(code = 500, message = "{code=2004 msg=sql execute error}"),
    })
    @GetMapping("/payment_percent_diagram")
    @ResponseBody
    public ResponseEntity paymentPercent(@RequestHeader(Constant.HEADER_STRING) String auth,
                                         @RequestParam String time) {
        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        PaymentPercentResponse response = statisticsService.paymentPercent(userId, time);
        return ResultBean.success(response);
    }

    // 图三
    // 基于图一的API，只返回每月总的Payment就可以了
    @ApiOperation(value = "获取某个部门或全部部门的总的月度实际支出。 departmentId=-1时为全部门. startTime endTime 格式 2020-01")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=0, msg='' data如下}", response = PaymentVariationResponse.class),
            @ApiResponse(code = 500, message = "{code=2003 msg='后台内部sql中table name错误（前端不用处理这个'}"),
            @ApiResponse(code = 403, message = "{code=1003, msg = \"user don't have enough permission to request this API\"}"),
            @ApiResponse(code = 404, message = "{code=1001 msg = \"request user not found in database\"}  {code=1002, msg= \"request department not found in database\"}")

    })
    @PostMapping("/payment_variation_diagram")
    @ResponseBody
    public ResponseEntity paymentVariationDiagram(@RequestHeader(Constant.HEADER_STRING) String auth,
                                                  @RequestBody PaymentVariationPayload payload) {

        Integer userId = authService.authorize(auth, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);
        statisticsService.checkPermission(userId, payload.getDepartmentId());

        var result = statisticsService.paymentVariation(payload.getDepartmentId(), payload.getStartTime(), payload.getEndTime());

        return ResultBean.success(result);
    }

    // 图五
    @GetMapping(value="/location_diagram")
    @ApiOperation(value = "获取某个时间段每个省份和城市的出差次数，包括起止月份，时间格式： yyyy-MM 如：2020-01")
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


    @GetMapping(value = "departmentcost_diagram")
    @ApiOperation(value = "获取某个时间段每个部门的花费;包括起止月份，格式： yyyy-MM 如：2020-01")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = DepartmentCost.class),
            @ApiResponse(code = 400, message = "{code=1004, msg='日期字符串格式错误，正确格式：yyyy-MM 如：2020-01'}", response = ResultBean.class)
    })
    public HttpEntity getDepartmentCost(
        @RequestHeader(Constant.HEADER_STRING) String auth,
        @RequestParam String startTime,
        @RequestParam String endTime
    ) {
        authService.authorize(auth, UserRoleEnum.Manager);

        List<DepartmentCost> departmentCosts = statisticsService.getDepartmentCost(startTime, endTime);
        return ResultBean.success(departmentCosts);
    }
}
