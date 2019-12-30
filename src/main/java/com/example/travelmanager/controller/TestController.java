package com.example.travelmanager.controller;

import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.service.auth.AuthException.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/token")
    public String addUser(@RequestParam String userName, @RequestParam String name)
    {
        return "";
    }

    @RequestMapping("/test")
    @ResponseBody
    public ResultBean testGetUser() {
        User u = new User();
        u.setName("test");
        return ResultBean.success(u);
    }

}
