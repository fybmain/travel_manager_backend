package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.dao.PaymentApplicationDao;
import com.example.travelmanager.entity.PaymentApplication;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.PaymentApplicationPayload;
import com.example.travelmanager.response.payment.PaymentApplicationResponse;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.service.payment.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentApplicationController {
    @Autowired
    private AuthService authService;

    @Autowired
    private PaymentApplicationDao paymentApplicationDao;

    @Autowired
    private PaymentService paymentService;


    // APIs
    @ApiOperation(value = "创建报销申请")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "创建成功 返回success 状态", response = ResultBean.class),
            @ApiResponse(code = 403, message = "无权限", response = ResultBean.class),
            @ApiResponse(code = 404, message = "未找到对应出差申请. {code = 1001, msg = The corresponding TravelApplication was not found}", response = ResultBean.class),
            @ApiResponse(code = 500, message = "创建失败 对应错误码 {code=2001, msg=Picture uploaded failed} ", response = ResultBean.class)
    })
    @PostMapping("/application")
    @ResponseBody
    public ResponseEntity create(@RequestHeader(Constant.HEADER_STRING) String auth, @RequestBody PaymentApplicationPayload paymentApplicationPayload) throws Exception {
        // 1. 验证token
        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        // 2. create PaymentApplication in database
        try {
            PaymentApplication paymentApplication = paymentService.createByPayload(paymentApplicationPayload, userId);
        }
        catch (Exception e) {
            throw e;
        }

        return ResultBean.success(HttpStatus.CREATED);
    }

    @GetMapping("/application")
    @ResponseBody
    public ResponseEntity get(@RequestHeader(Constant.HEADER_STRING) String auth, @RequestParam Integer id) throws Exception {
        PaymentApplicationResponse response = paymentService.getById(id);

        return ResultBean.success(response);
    }
}
