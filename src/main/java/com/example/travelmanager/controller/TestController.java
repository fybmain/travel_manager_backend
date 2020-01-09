package com.example.travelmanager.controller;

import java.util.List;

import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.dao.StatisticsDao;
import com.example.travelmanager.dao.TravelApplicationDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.response.statistics.DepartmentCost;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.service.email.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TravelApplicationDao travelApplicationDao;

    @Autowired
    private StatisticsDao statisticsDao;

    @RequestMapping("/email")
    public HttpEntity email(String to, String content, String subject){
        emailService.sendSimpleMail(to, subject, content);
        throw new RuntimeException("test");
        //return ResultBean.success();
    }

    @GetMapping(value="/test")
    public HttpEntity test() {
        
        List<Object[]> departmentCosts = null;
        return ResultBean.success(departmentCosts);
    }

    
}
