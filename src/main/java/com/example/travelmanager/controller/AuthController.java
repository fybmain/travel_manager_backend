package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.payload.LoginPayload;
import com.example.travelmanager.payload.RegisterPayload;
import com.example.travelmanager.payload.ResetPasswordPayload;
import com.example.travelmanager.service.auth.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import response.TokenResponse;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserDao userDao;

    @PostMapping("/token")
    @ApiOperation(value = "获取token", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "code = 0", response = TokenResponse.class),
            @ApiResponse(code = 401, message = "username or password incorrect", response = ResultBean.class)
    })
    public HttpEntity GetToken(@Validated @RequestBody LoginPayload loginPayload) {
        User user = userDao.findByWorkId(loginPayload.getWorkId());
        if (user == null || !user.validPassword(loginPayload.getPassword())) {
            return ResultBean.error(HttpStatus.UNAUTHORIZED, 401, "username or password incorrect");
        }
        TokenResponse token = authService.generateTokenResponse(user);
        return ResultBean.success(token);
    }

    @GetMapping("/token")
    @ApiOperation(value = "刷新token", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "code = 0", response = TokenResponse.class),
            @ApiResponse(code = 401, message = "not a valid token", response = ResultBean.class)
    })
    public HttpEntity refreshToken(@RequestHeader(Constant.HEADER_STRING) String auth) {
        int uid = authService.authorize(auth);
        User user = userDao.findById(uid).get();
        return ResultBean.success((authService.generateTokenResponse(user)));
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册新用户", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "{code=0,msg='success'}", response = ResultBean.class),
            @ApiResponse(code = 400, message = "{code=1001,msg='work id exists'}", response = ResultBean.class)
    })
    public HttpEntity register(@Validated @RequestBody RegisterPayload registerPayload) {
        authService.register(registerPayload);
        return ResultBean.success(HttpStatus.CREATED);
    }

    @PostMapping("/resetpassword")
    @ApiOperation(value = "修改密码", response = ResultBean.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "{code=0,msg='success'}", response = ResultBean.class),
        @ApiResponse(code = 400, message = "{code=1002, msg='密码错误'", response = ResultBean.class)
    })
    public HttpEntity resetPassword(
        @RequestHeader(Constant.HEADER_STRING) String auth,
        @RequestBody ResetPasswordPayload resetPasswordPayload
    ){
        int uid = authService.authorize(auth);
        authService.resetPassword(uid, resetPasswordPayload);
        return ResultBean.success();
    }
}