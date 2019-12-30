package com.example.travelmanager.service.auth;

import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.RegisterErrorEnum;
import com.example.travelmanager.payload.RegisterPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;

    @Override
    public ResponseEntity register(RegisterPayload registerPayload) {

        if (userDao.findByWorkId(registerPayload.getWorkId()) != null) {
            return ResponseEntity.ok(RegisterErrorEnum.WORKIDEXIST);
        }
        User user = new User();
        user.setName(registerPayload.getName());
        user.setEmail(registerPayload.getEmail());
        user.setTelephone(registerPayload.getTelephone());
        user.setPassword(registerPayload.getPassword());
        user.setWorkId(registerPayload.getWorkId());
        user.setRole(1);
        user.setStatus(false);
        userDao.save(user);
        return ResponseEntity.ok(RegisterErrorEnum.SUCCESS);
    }
}
