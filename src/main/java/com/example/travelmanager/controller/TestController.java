package com.example.travelmanager.controller;

import com.example.travelmanager.dao.UserRepository;
import com.example.travelmanager.service.auth.AuthException.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping("/test")
    public String test() {

        throw new UnauthorizedException();
    }

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/token")
    public String addUser(@RequestParam String userName, @RequestParam String name)
    {
        return "";
    }
}
