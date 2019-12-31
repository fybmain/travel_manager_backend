package com.example.travelmanager.config.WebException;

public class BadRequestException extends RuntimeException{
    private Integer errCode;

    public BadRequestException(String msg) {
        super(msg);
        this.errCode = 400;
    }

    public BadRequestException(String msg, Integer errCode) {
        super(msg);
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }
}
