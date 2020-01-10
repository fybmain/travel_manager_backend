package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.entity.PaymentApplication;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.ApprovalPayload;
import com.example.travelmanager.payload.PaymentApplicationPayload;
import com.example.travelmanager.response.payment.PaymentApplicationResponse;
import com.example.travelmanager.response.payment.SimplePaymentListResponse;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.service.payment.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentApplicationController {
    @Autowired
    private AuthService authService;

    @Autowired
    private PaymentService paymentService;


    // APIs
    // 1.travelApply 判断是否通过
    // 2. picture判断是否存在

    // 3.判断travel的paid是否已经为true，已经为true则抛异常返回
    // 4.提交了申请就把对应的travel的paid改成true, approve中申请没通过再改成false.
    @ApiOperation(value = "创建报销申请")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "创建成功 返回success 状态", response = ResultBean.class),
            @ApiResponse(code = 403, message = "{code=1005, msg = TravelApplication not approved, can't add payment application} \n " +
                    "{code = 4003 msg = This travel application have ongoing payment application already}", response = ResultBean.class),
            @ApiResponse(code = 404, message = "未找到对应出差申请： {code = 1001, msg = The corresponding TravelApplication was not found}" +
                    "\n {code = 1006, msg = Picture NotFound}", response = ResultBean.class),
            @ApiResponse(code = 500, message = "未知错误", response = ResultBean.class)
    })
    @PostMapping("/application")
    @ResponseBody
    public ResponseEntity create(@RequestHeader(Constant.HEADER_STRING) String auth, @RequestBody PaymentApplicationPayload paymentApplicationPayload)  {
        // 1. 验证token
        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        // 2. create PaymentApplication in database
        PaymentApplication paymentApplication = paymentService.createByPayload(paymentApplicationPayload, userId);

        return ResultBean.success(HttpStatus.CREATED);
    }


    @ApiOperation(value = "报销申请详情信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取成功", response = PaymentApplicationResponse.class),
            @ApiResponse(code = 403, message = "用户无查看该申请的权限.", response = ResultBean.class),
            @ApiResponse(code = 404, message = "未找到对应出差申请：未找到该报销申请中的出差申请: {code = 1001, msg = ...} \n " +
                                               "未找到该报销申请: {code=1002, msg = ...} \n  " +
                                               "未找到该用户： {code=1003, msg = ...}        ", response = ResultBean.class),
            @ApiResponse(code = 500, message = "未知错误", response = ResultBean.class)
    })
    @GetMapping("/application")
    @ResponseBody
    public ResponseEntity get(@RequestHeader(Constant.HEADER_STRING) String auth, @RequestParam Integer applyId) {
        // 验证token
        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        PaymentApplicationResponse response = paymentService.getById(userId, applyId);

        return ResultBean.success(response);
    }


    @ApiOperation(value = "获取报销申请列表. ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取成功", response = SimplePaymentListResponse.class),
            @ApiResponse(code = 400, message = "请求参数错误 {code=3001 msg=state params error} {code=3002 msg = departmentId should >= -1}", response = ResultBean.class),
            @ApiResponse(code = 403, message = "无权限", response = ResultBean.class),
            @ApiResponse(code = 404, message = "未找到该用户： {code=1003, msg = ...}", response = ResultBean.class),
            @ApiResponse(code = 500, message = "未知错误", response = ResultBean.class)
    })
    @GetMapping("/applications")
    @ResponseBody
    public ResponseEntity listUncheck(@RequestHeader(Constant.HEADER_STRING) String auth,
                                     @RequestParam(defaultValue = "5") Integer size,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @ApiParam(name = "state", value = "申请状态, 包括finished unfinished all, 即为未完成，已完成和全部") @RequestParam(defaultValue = "all") String state,
                                     @ApiParam(name = "departmentId", value = "部门id，为-1时为所有部门") @RequestParam(defaultValue = "-1") Integer departmentId) {
        // 验证token
        // 不允许普通员工访问
        Integer userId = authService.authorize(auth, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);
        // 查询中page从0开始，而前端从1开始，因此在这里-1；
        page = page-1;
        if(page < 0) {
            page = 0;
        }

        SimplePaymentListResponse response = paymentService.listApplications(userId, size, page, state, departmentId);

        return ResultBean.success(response);
    }


    @ApiOperation(value = "获取当前用户的报销申请列表.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取成功", response = SimplePaymentListResponse.class),
            @ApiResponse(code = 400, message = "请求参数错误 {code=3001 msg=state params error} {code=3002 msg = departmentId should >= -1}", response = ResultBean.class),
            @ApiResponse(code = 403, message = "无权限", response = ResultBean.class),
            @ApiResponse(code = 500, message = "未知错误", response = ResultBean.class)
    })
    @GetMapping("/applications/me")
    @ResponseBody
    public ResponseEntity listMyUncheck(@RequestHeader(Constant.HEADER_STRING) String auth,
                                        @RequestParam(defaultValue = "5") Integer size,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @ApiParam(name = "state", value = "申请状态, 包括finished unfinished all, 即为未完成，已完成和全部") @RequestParam(defaultValue = "all") String state) {
        // 验证token
        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);
        // 查询中page从0开始，而前端从1开始，因此在这里-1；
        page = page-1;
        if(page < 0) {
            page = 0;
        }

        SimplePaymentListResponse response = paymentService.listMyApplications(userId, size, page, state);

        return ResultBean.success(response);
    }


    @ApiOperation(value = "审核一个报销申请")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=200, msg='success'}", response = ResultBean.class),
            @ApiResponse(code = 403, message = "{code=4001, msg='application state is approved OR unapproved, can't modify'}\n" +
                    "{code=4002, msg=you can not approve this application now}", response = ResultBean.class),
            @ApiResponse(code = 404, message = "{code=1001 msg=Travel Application Not Found} \n " +
                    "{code=1002, msg='PaymentApplication not found'}", response = ResultBean.class),
    })
    @PutMapping("/approval")
    @ResponseBody
    public ResponseEntity approve(@RequestHeader(Constant.HEADER_STRING) String auth,
                                 @Validated @RequestBody ApprovalPayload approvalPayload) {
        Integer uid = authService.authorize(auth, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        paymentService.approve(uid, approvalPayload.getApplyId(), approvalPayload.getApproved(), approvalPayload.getComment());

        return ResultBean.success();
    }
}
