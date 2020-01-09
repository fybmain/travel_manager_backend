package com.example.travelmanager.service;

import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.UserRoleEnum;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InitDB implements InitializingBean {

    @Autowired
    private UserDao userDao;

    @Override
    public void afterPropertiesSet() throws Exception {
        initUser();
    }

    private void initUser() {
        if (userDao.findByWorkId("admin") == null) {
            User user = new User();
            user.setName("admin");
            user.setStatus(true);
            user.setWorkId("admin");
            user.setRole(UserRoleEnum.Admin.getRoleId());
            user.setPassword("997icu");
            userDao.save(user);
        }
    }

    
}