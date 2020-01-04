package com.example.travelmanager.config.exception;

import org.springframework.http.HttpStatus;

public class AdminControllerException extends ErrorException {
    private static final long serialVersionUID = 1L;

    private static final Integer userNotExists = 1001;
    private static final String userNotExistsMsg = "用户不存在";
    public static final AdminControllerException userNotExistException =
        new AdminControllerException(userNotExists,userNotExistsMsg, HttpStatus.NOT_FOUND);

    private static final Integer userCanNotChange = 1002;
    private static final String userCanNotChangeMsg = "用户不能被修改";
    public static final AdminControllerException userCanNotChangeException =
        new AdminControllerException(userCanNotChange,userCanNotChangeMsg, HttpStatus.NOT_FOUND);

    // 构造函数
    private AdminControllerException(int code, String msg, HttpStatus httpStatus) {
        super(code, msg, httpStatus);
    }
}
