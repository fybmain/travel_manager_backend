package com.example.travelmanager.controller;


import com.example.travelmanager.config.WebException.*;
import com.example.travelmanager.controller.bean.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity unknownExceptionHandler(Exception e) {
        logger.error("发生了未知异常", e);
        // 发送邮件通知技术人员.
        return ResultBean.error(HttpStatus.INTERNAL_SERVER_ERROR, -99, e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity UnauthorizedExceptionHandler(UnauthorizedException e) {
        return ResultBean.error(HttpStatus.UNAUTHORIZED, 401, "未认证");
    }

    @ExceptionHandler
    public ResponseEntity ForbiddenExceptionHandler(ForbiddenException e) {
        return ResultBean.error(HttpStatus.FORBIDDEN, 403, "禁止访问");
    }

    // 业务错误
    @ExceptionHandler
    public ResponseEntity TravelApplicationNotFoundHandler(ErrorException e) {
        return ResultBean.error(e);
    }

    @ExceptionHandler
    public ResponseEntity HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        return ResultBean.error(HttpStatus.METHOD_NOT_ALLOWED, 405, "method not allowed");
    }

    @ExceptionHandler
    public ResponseEntity BadRequestExceptionHandler (BadRequestException e) {
        return ResultBean.error(HttpStatus.BAD_REQUEST, e.getErrCode(), e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return ResultBean.error(HttpStatus.BAD_REQUEST, 400, e.getMessage());
    }
}

