package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.response.message.MessageResponse;
import com.example.travelmanager.service.auth.AuthService;
import com.example.travelmanager.service.message.MessageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private AuthService authService;

    @Autowired
    private MessageService messageService;


    @ApiOperation(value = "获取Msg")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok", response = MessageResponse.class),
    })
    @GetMapping("/all")
    public ResponseEntity getMyMessage(@RequestHeader(Constant.HEADER_STRING) String auth) {
        // 验证token
        Integer userId = authService.authorize(auth, UserRoleEnum.Employee, UserRoleEnum.DepartmentManager, UserRoleEnum.Manager);

        var messages = messageService.getMessages(userId);

        return ResultBean.success(messages);
    }

}
