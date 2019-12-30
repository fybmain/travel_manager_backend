package com.example.travelmanager.controller.bean;

import java.util.Collection;

// https://juejin.im/post/5c3ea92a5188251e101598aa#heading-6
public class ResultBean<T> {
    private int code;
    private String message;
    private T data;

    private ResultBean() { }

    public static ResultBean error(int code, String message) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setMessage(message);
        return resultBean;
    }

    public static ResultBean success() {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        return resultBean;
    }

    public static <V>  ResultBean<V> success(V data) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(0);
        resultBean.setMessage("success");
        resultBean.setData(data);
        return resultBean;
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
