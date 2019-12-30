package com.example.travelmanager.controller.bean;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

// https://juejin.im/post/5c3ea92a5188251e101598aa#heading-6
public class ResultBean<T> {
    private int code;
    private String message;
    private T data;

    private ResultBean() { }

    public static ResponseEntity error(HttpStatus httpStatus, int code, String message) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setMessage(message);

        return ResponseEntity.status(httpStatus).body(resultBean);
    }

    public static ResponseEntity success() {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        return ResponseEntity.ok(resultBean);
    }

    public static ResponseEntity success(HttpStatus httpStatus) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        return ResponseEntity.status(httpStatus).body(resultBean);
    }

    public static <V> ResponseEntity success(V data) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        resultBean.setData(data);
        return ResponseEntity.ok(resultBean);
    }

    public static <V> ResponseEntity success(HttpStatus httpStatus, V data) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        resultBean.setData(data);
        return ResponseEntity.status(httpStatus).body(resultBean);
    }


    public int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    private void setData( T data) {
        this.data = data;
    }

}
