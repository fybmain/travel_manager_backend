package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.TravelApplicationPayload;
import com.example.travelmanager.service.TravelApplication.TravelApplicationService;
import com.example.travelmanager.service.auth.AuthService;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation(value = "travel application", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=0,msg='success'}", response = ResultBean.class),
            @ApiResponse(code = 400, message = "{code=400,msg='bad request'};", response = ResultBean.class)
    })
    @PostMapping("/application")
    public HttpEntity TravelApply(
            @RequestHeader(Constant.HEADER_STRING) String auth,
            @Validated @RequestBody TravelApplicationPayload travelApplicationPayload
    ) {
        int uid = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);
        travelApplicationService.TravelApply(uid, travelApplicationPayload);

        return ResultBean.success(HttpStatus.CREATED);
    }
}
