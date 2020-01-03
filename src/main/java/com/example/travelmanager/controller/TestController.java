package com.example.travelmanager.controller;

import com.example.travelmanager.dao.TravelApplicationDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private TravelApplicationDao travelApplicationDao;

    @RequestMapping("/test")
    @ResponseBody
    public HttpEntity access(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("test");
    }
}
