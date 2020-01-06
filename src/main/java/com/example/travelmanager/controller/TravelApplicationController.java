package com.example.travelmanager.controller;

import java.util.List;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.ApprovalPayload;
import com.example.travelmanager.payload.TravelApplicationPayload;
import com.example.travelmanager.response.travel.DetailTravelApplication;
import com.example.travelmanager.response.travel.ProvinceAndTimesResponse;
import com.example.travelmanager.response.travel.TravelApplicationsResponse;
import com.example.travelmanager.service.travel.TravelApplicationService;
import com.example.travelmanager.service.auth.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/travel")
public class TravelApplicationController {
    @Autowired
    private AuthService authService;

    @Autowired
    private TravelApplicationService travelApplicationService;


    @ApiOperation(value = "提交报销申请", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "{code=0,msg='success'}", response = ResultBean.class),
            @ApiResponse(code = 400, message = "{code=400,msg='bad request'};", response = ResultBean.class)
    })
    @PostMapping("/application")
    public HttpEntity TravelApply(
            @RequestHeader(Constant.HEADER_STRING) String auth,
            @Validated @RequestBody TravelApplicationPayload travelApplicationPayload
    ) {
        int uid = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);
        travelApplicationService.travelApply(uid, travelApplicationPayload);

        return ResultBean.success(HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取一个报销申请（通过申请ID）", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = DetailTravelApplication.class),
            @ApiResponse(code = 404, message = "{code=1001, msg='TravelApplication not found'}", response = ResultBean.class),
            @ApiResponse(code = 403, message = "{code=1002, msg='Not allowed to access this application'}", response = ResultBean.class),
    })
    @GetMapping("/application")
    public HttpEntity getApplication(
            @RequestHeader(Constant.HEADER_STRING) String auth,
            @RequestParam int applyId
    ) {
        int uid = authService.authorize(auth);
        DetailTravelApplication detailTravelApplication = travelApplicationService.getTravelApplication(uid, applyId);
        return ResultBean.success(detailTravelApplication);
    }

    @GetMapping("/applications/me")
    @ApiOperation(value = "获取当前用户的提交申请列表", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = TravelApplicationsResponse.class),
            @ApiResponse(code = 400, message = "{code=1003, msg='state must be Finished, Unfinished or All'}", response = ResultBean.class)
    })
    public HttpEntity getApplicationsMe(
            @RequestHeader(Constant.HEADER_STRING) String auth,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size,
            @ApiParam(value = "all, finished, unfinished")  @RequestParam(defaultValue = "All") String state
    ) {
        int uid = authService.authorize(auth);
        TravelApplicationsResponse travelApplicationsResponse = travelApplicationService.getTravelApplications(uid, page, size, state);
        return ResultBean.success(travelApplicationsResponse);
    }

    @GetMapping("/applications")
    @ApiOperation(value = "经理、部门经理获取申请列表", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = TravelApplicationsResponse.class),
            @ApiResponse(code = 400, message = "{code=1003, msg='state must be Finished, Unfinished or All'}", response = ResultBean.class)
    })
    public HttpEntity getApplications(
            @RequestHeader(Constant.HEADER_STRING) String auth,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size,
            @ApiParam(value = "all, finished, unfinished")  @RequestParam(defaultValue = "All") String state,
            @ApiParam(value = "-1 for all department") @RequestParam(defaultValue = "-1") Integer departmentId
    ) {
        int uid = authService.authorize(auth, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager, UserRoleEnum.Admin);

        TravelApplicationsResponse travelApplicationsResponse =
                travelApplicationService.getTravelApplicationsByDepartmentId(uid, page, size, state, departmentId);
        return ResultBean.success(travelApplicationsResponse);
    }

    @ApiOperation(value = "获取未报销的出差申请", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = TravelApplicationsResponse.class),
    })
    @GetMapping("/applications/unpaid")
    public HttpEntity getUnpaidApplications(
            @RequestHeader(Constant.HEADER_STRING) String auth,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        int uid = authService.authorize(auth);
        TravelApplicationsResponse travelApplicationsResponse =  travelApplicationService.getTravelUnpaidApplication(uid,page, size);
        return ResultBean.success(travelApplicationsResponse);
    }

    @PutMapping("/approval")
    @ApiOperation(value = "审核出差申请")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = ResultBean.class),
            @ApiResponse(code = 404, message = "{code=1001, msg='TravelApplication not found'}", response = ResultBean.class),
            @ApiResponse(code = 403, message = "{code=1002, msg='Not allowed to access this application'}", response = ResultBean.class)
    })
    public HttpEntity travelApproval(
            @RequestHeader(Constant.HEADER_STRING) String auth,
            @Validated @RequestBody ApprovalPayload approvalPayload
    ){
        int uid = authService.authorize(auth, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);
        travelApplicationService.travelApproval(uid, approvalPayload);
        return ResultBean.success();
    }

    @GetMapping(value="/statistic/location")
    @ApiOperation(value = "获取某个时间段每个省份和城市的出差次数，包括起止月份，月份格式： yyyy/MM 如：2020/01")
    @ApiResponses({
        @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = ProvinceAndTimesResponse.class),
        @ApiResponse(code = 400, message = "{code=1004, msg='日期字符串格式错误，正确格式：yyyy/MM 如：2020/01'}", response = ResultBean.class)
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
