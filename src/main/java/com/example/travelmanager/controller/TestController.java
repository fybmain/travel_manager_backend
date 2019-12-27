package com.example.travelmanager.controller;

import com.example.travelmanager.dao.UserRepository;
import com.example.travelmanager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "Test";
    }

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/adduser")
    public User addUser(@RequestParam String userName, @RequestParam String name)
    {
        User u = new User();
        u.setUserName(userName);
        u.setName(name);
        userRepository.save(u);

        return u;
    }
}
