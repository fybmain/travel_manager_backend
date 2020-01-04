package com.example.travelmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.response.admin.UsersResponse;
import com.example.travelmanager.service.admin.AdminService;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.enums.UserRoleEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


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
        @RequestHeader(Constant.HEADER_STRING) String auth,
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "8") Integer size
    ) {
        authService.authorize(auth, UserRoleEnum.Admin);
        UsersResponse usersResponse = adminService.getUsers(page, size);
        return ResultBean.success(usersResponse);
    }
    

}