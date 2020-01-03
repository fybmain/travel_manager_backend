package com.example.travelmanager.config.WebException;

import org.springframework.http.HttpStatus;

public class AuthControllerException extends ErrorException {
    private static final Integer WorkIdExists = 1001;
    private static final String WorkIdExistsMsg = "work id exists";
    public static final AuthControllerException WorkIdNotExistException =
            new AuthControllerException(WorkIdExists,WorkIdExistsMsg, HttpStatus.BAD_REQUEST);


    // 构造函数
    private AuthControllerException(int code, String msg, HttpStatus httpStatus) {
        super(code, msg, httpStatus);
    }
}
