package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.dao.PaymentApplicationDao;
import com.example.travelmanager.entity.PaymentApplication;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.PaymentApplicationPayload;
import com.example.travelmanager.response.payment.PaymentApplicationResponse;
import com.example.travelmanager.response.payment.SimplePaymentListResponse;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.service.payment.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
            @ApiResponse(code = 201, message = "创建成功 返回success 状态", response = ResultBean.class),
            @ApiResponse(code = 403, message = "无权限", response = ResultBean.class),
            @ApiResponse(code = 404, message = "未找到对应出差申请： {code = 1001, msg = The corresponding TravelApplication was not found}", response = ResultBean.class),
            @ApiResponse(code = 500, message = "未知错误", response = ResultBean.class)
    })
    @PostMapping("/application")
    @ResponseBody
    public ResponseEntity create(@RequestHeader(Constant.HEADER_STRING) String auth, @RequestBody PaymentApplicationPayload paymentApplicationPayload)  {
        // 1. 验证token
        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        // 2. create PaymentApplication in database
        try {
            PaymentApplication paymentApplication = paymentService.createByPayload(paymentApplicationPayload, userId);
        }
        catch (RuntimeException e) {
            throw e;
        }

        return ResultBean.success(HttpStatus.CREATED);
    }


    @ApiOperation(value = "报销申请详情信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取成功", response = PaymentApplicationResponse.class),
            @ApiResponse(code = 403, message = "无权限", response = ResultBean.class),
            @ApiResponse(code = 404, message = "未找到对应出差申请：未找到该报销申请中的出差申请: {code = 1001, msg = ...} \n " +
                                               "未找到该报销申请: {code=1002, msg = ...} \n  " +
                                               "未找到该用户： {code=1003, msg = ...}        ", response = ResultBean.class),
            @ApiResponse(code = 500, message = "未知错误", response = ResultBean.class)
    })
    @GetMapping("/application")
    @ResponseBody
    public ResponseEntity get(@RequestHeader(Constant.HEADER_STRING) String auth, @RequestParam Integer id) {
        // 验证token
        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        PaymentApplicationResponse response = paymentService.getById(id);

        return ResultBean.success(response);
    }

    @GetMapping("/canpay")
    @ResponseBody
    public ResponseEntity listCanPay(@RequestHeader(Constant.HEADER_STRING) String auth,
                                     @RequestParam(defaultValue = "5") Integer size,
                                     @RequestParam(defaultValue = "1") Integer page) {
        // 验证token
        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);
        // 查询中page从0开始，而前端从1开始，因此在这里-1；
        page = page-1;
        if(page < 0) {
            page = 0;
        }

        SimplePaymentListResponse response = paymentService.listCanPay(userId, size, page);

        return ResultBean.success(response);
    }
}
