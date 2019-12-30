package com.example.travelmanager.enums;

public enum RegisterErrorEnum {
    SUCCESS("Success", 0),
    WORKIDEXIST("WorkId exists", 1);

    private String msg;

    private int code;

    private RegisterErrorEnum(String msg, int state) {
        this.msg = msg;
        this.code = state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}

