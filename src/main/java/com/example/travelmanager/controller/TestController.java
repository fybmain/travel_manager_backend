package com.example.travelmanager.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "Test";
    }
}
