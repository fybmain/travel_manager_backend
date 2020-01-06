package com.example.travelmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.response.admin.UsersResponse;
import com.example.travelmanager.service.admin.AdminService;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.ApproveUserPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "管理员获取所有用户", response = ResultBean.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "code = 0", response = UsersResponse.class)
    })
    @GetMapping(value="/users")
    public HttpEntity getUsers(
        @RequestHeader(Constant.HEADER_STRING) final String auth,
            @RequestParam(defaultValue = "1") final Integer page, @RequestParam(defaultValue = "8") final Integer size,
            @ApiParam("true已审批账户，false未审批账户") @RequestParam(defaultValue = "false") final Boolean enable) {
        authService.authorize(auth, UserRoleEnum.Admin);
        final UsersResponse usersResponse = adminService.getUsers(enable, page, size);
        return ResultBean.success(usersResponse);
    }

    @PutMapping(value = "/user/approval")
    @ApiOperation(value = "审核用户账号", response = ResultBean.class)
    @ApiResponses({ @ApiResponse(code = 200, message = "code = 0", response = ResultBean.class),
            @ApiResponse(code = 404, message = "{code=1001,msg='用户不存在'}", response = ResultBean.class),
            @ApiResponse(code = 400, message = "{code=1002,msg='用户不能被修改'}", response = ResultBean.class) })
    public HttpEntity approveUser(@RequestHeader(Constant.HEADER_STRING) final String auth,
            @Validated @RequestBody final ApproveUserPayload approveUserPayload
    ) {
        authService.authorize(auth, UserRoleEnum.Admin);
        
        adminService.approveUser(approveUserPayload);
        
        return ResultBean.success();
    }
}