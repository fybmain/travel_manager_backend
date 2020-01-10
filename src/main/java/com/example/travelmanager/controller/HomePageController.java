package com.example.travelmanager.controller;


import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.response.homepage.HomePageResponse;
import com.example.travelmanager.response.payment.SimplePaymentListResponse;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.service.payment.PaymentService;
import com.example.travelmanager.service.travel.TravelApplicationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/homepage")
public class HomePageController {
    @Autowired
    private AuthService authService;

    @Autowired
    private TravelApplicationService travelApplicationService;

    @Autowired
    private PaymentService paymentService;


    @ApiOperation(value = "获取首页申请列表 ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取成功", response = HomePageResponse.class)
    })
    @GetMapping("/applications")
    @ResponseBody
    public ResponseEntity list(@RequestHeader(Constant.HEADER_STRING) String auth,
                               @ApiParam(name = "type", value = "travel 或者 payment") @RequestParam(defaultValue = "payment") String type,
                               @ApiParam(name = "size", value = "") @RequestParam(defaultValue = "5") Integer size) {

        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        HomePageResponse response = new HomePageResponse();
        if(type.equals("payment")) {
            response = paymentService.listHomePageApplications(userId, size);
        } else if(type.equals("travel")) {
            response = travelApplicationService.listHomePageApplications(userId, size);
        }

        return ResultBean.success(response);
    }
}
