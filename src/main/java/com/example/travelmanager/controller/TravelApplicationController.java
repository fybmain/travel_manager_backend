package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.entity.TravelApplication;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.TravelApplicationPayload;
import com.example.travelmanager.payload.ApprovalPayload;
import com.example.travelmanager.response.travel.TravelApplicationsResponse;
import com.example.travelmanager.service.TravelApplication.TravelApplicationService;
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

@RestController
@RequestMapping("/api/travel")
public class TravelApplicationController {
    @Autowired
    private AuthService authService;

    @Autowired
    private TravelApplicationService travelApplicationService;


    @ApiOperation(value = "submit travel application", response = ResultBean.class)
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

    @ApiOperation(value = "get a travel application", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = TravelApplication.class),
            @ApiResponse(code = 404, message = "{code=1001, msg='TravelApplication not found'}", response = ResultBean.class),
            @ApiResponse(code = 403, message = "{code=1002, msg='Not allowed to access this application'}", response = ResultBean.class),
    })
    @GetMapping("/application")
    public HttpEntity getApplication(
            @RequestHeader(Constant.HEADER_STRING) String auth,
            @RequestParam int applyId
    ) {
        int uid = authService.authorize(auth);
        TravelApplication travelApplication = travelApplicationService.getTravelApplication(uid, applyId);
        return ResultBean.success(travelApplication);
    }

    @GetMapping("/applications/me")
    @ApiOperation(value = "get travel applications of current login user", response = ResultBean.class)
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
    @ApiOperation(value = "(department) manager get all travel applications by department", response = ResultBean.class)
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

    @PutMapping("/approval")
    @ApiOperation(value = "approve a travel application")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = ResultBean.class),
            @ApiResponse(code = 404, message = "{code=1001, msg='TravelApplication not found'}", response = ResultBean.class),
            @ApiResponse(code = 403, message = "{code=1002, msg='Not allowed to access this application'}", response = ResultBean.class),
    })
    public HttpEntity travelApproval(
            @RequestHeader(Constant.HEADER_STRING) String auth,
            @Validated @RequestBody ApprovalPayload approvalPayload
    ){
        int uid = authService.authorize(auth, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);
        travelApplicationService.travelApproval(uid, approvalPayload);
        return ResultBean.success();
    }
}
