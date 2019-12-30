package com.example.travelmanager.controller;

import com.alibaba.fastjson.JSON;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.payload.LoginPayload;
import com.example.travelmanager.payload.RegisterPayload;
import com.example.travelmanager.service.auth.AuthHelper;
import com.example.travelmanager.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserDao userDao;

    @PostMapping("/token")
    public HttpEntity GetToken(@RequestBody LoginPayload loginPayload) {
        User user = userDao.findByWorkId(loginPayload.getWorkid());
        System.out.println(JSON.toJSONString(user));
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        if (user == null || !user.validPassword(loginPayload.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(AuthHelper.generateTokenResponse(user));
    }

    @PostMapping("/register")
    public HttpEntity Register(@RequestBody RegisterPayload registerPayload) {
        return authService.register(registerPayload);
    }
}
