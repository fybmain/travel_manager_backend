package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.dao.TravelApplicationDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

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
    public HttpEntity access(@RequestHeader(Constant.HEADER_STRING) String auth) {
        int uid = authService.authorize(auth);
        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");
        return ResultBean.success(travelApplicationDao.findAllWithState(set, pageable).toList());
    }
}
