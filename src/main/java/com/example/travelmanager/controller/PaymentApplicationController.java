package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.dao.PaymentApplicationDao;
import com.example.travelmanager.entity.PaymentApplication;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.PaymentApplicationPayload;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.service.payment.PaymentService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/")
public class PaymentApplicationController {
    @Autowired
    private AuthService authService;

    @Autowired
    private PaymentApplicationDao paymentApplicationDao;

    @Autowired
    private PaymentService paymentService;


    @PostMapping("application")
    @ResponseBody
    public ResponseEntity create(@RequestHeader(Constant.HEADER_STRING) String auth, @RequestBody PaymentApplicationPayload paymentApplicationPayload) {
        // 1. 验证token
        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        // 2. create PaymentApplication in database
        PaymentApplication paymentApplication = paymentService.createByPayload(paymentApplicationPayload, userId);


        return ResultBean.success(HttpStatus.CREATED);
    }
}
