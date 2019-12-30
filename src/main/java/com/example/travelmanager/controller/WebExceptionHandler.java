package com.example.travelmanager.controller;


import com.example.travelmanager.controller.bean.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity unknownException(Exception e) {
        logger.error("发生了未知异常", e);
        // 发送邮件通知技术人员.
        return ResultBean.error(HttpStatus.INTERNAL_SERVER_ERROR, -99, "未知错误");
    }
}

