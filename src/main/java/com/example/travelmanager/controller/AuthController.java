package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.payload.EditUserPaylod;
import com.example.travelmanager.payload.ForgetPasswordPayload;
import com.example.travelmanager.payload.LoginPayload;
import com.example.travelmanager.payload.RegisterPayload;
import com.example.travelmanager.payload.ResetPasswordPayload;
import com.example.travelmanager.payload.SetPasswordPayload;
import com.example.travelmanager.response.TokenResponse;
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
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/token")
    @ApiOperation(value = "获取token", response = ResultBean.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=0, msg='success'}", response = TokenResponse.class),
            @ApiResponse(code = 401, message = "{code=1003, msg='用户名或密码错误'}", response = ResultBean.class),
            @ApiResponse(code = 423, message = "{code=1004, msg='用户不可登录'}", response = ResultBean.class)
    })
    public HttpEntity GetToken(@Validated @RequestBody LoginPayload loginPayload) {
        TokenResponse token = authService.getToken(loginPayload);
        return ResultBean.success(token);
    }

    @GetMapping("/token")
    @ApiOperation(value = "刷新token", response = ResultBean.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "{code=0, msg='success'}", response = TokenResponse.class),
        @ApiResponse(code = 401, message = "{code=401, msg='token不合法或者已过期'}", response = ResultBean.class),
        @ApiResponse(code = 423, message = "{code=1004, msg='用户不可登录'}", response = ResultBean.class)
    })
    public HttpEntity refreshToken(@RequestHeader(Constant.HEADER_STRING) String auth) {
        authService.authorize(auth);
        return ResultBean.success((authService.refershToken(auth)));
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

    @PostMapping("/setpassword")
    @ApiOperation(value = "修改密码", response = ResultBean.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "{code=0,msg='success'}", response = ResultBean.class),
        @ApiResponse(code = 400, message = "{code=1002, msg='密码错误'", response = ResultBean.class)
    })
    public HttpEntity resetPassword(
        @RequestHeader(Constant.HEADER_STRING) String auth,
        @RequestBody SetPasswordPayload setPasswordPayload
    ){
        int uid = authService.authorize(auth);
        authService.setPassword(uid, setPasswordPayload);
        return ResultBean.success();
    }

    @PutMapping("/user")
    @ApiOperation(value = "修改用户信息", response = ResultBean.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "{code=0,msg='success'}", response = ResultBean.class)
    })
    public HttpEntity editUser(
        @RequestHeader(Constant.HEADER_STRING) String auth,
        @RequestBody EditUserPaylod editUserPaylod
    ){
        int uid = authService.authorize(auth);
        authService.editUser(uid, editUserPaylod);
        return ResultBean.success();
    }

    @PostMapping("/forgetpassword")
    @ApiOperation(value = "找回密码", response = ResultBean.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "{code=0, msg='success'}", response = ResultBean.class),
        @ApiResponse(code = 404, message = "{code=1006, msg='用户不存在'}", response = ResultBean.class),
        @ApiResponse(code = 400, message = "{code=1005, msg='邮箱错误'}", response = ResultBean.class)
    })
    public HttpEntity forgetPassword(
        @Validated @RequestBody ForgetPasswordPayload forgetPasswordPayload
    ){
        authService.forgetPassword(forgetPasswordPayload);
        return ResultBean.success();
    }

    @PostMapping("/resetpassword")
    @ApiOperation(value = "重置密码", response = ResultBean.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "{code=0, mag='success'}", response = ResultBean.class),
        @ApiResponse(code = 403, message = "", response = ResultBean.class)
    })
    public HttpEntity reSetPassword(
        @Validated @RequestBody ResetPasswordPayload resetPasswordPayload
    ){
        authService.resetPasswoed(resetPasswordPayload);
        return ResultBean.success();
    }
}