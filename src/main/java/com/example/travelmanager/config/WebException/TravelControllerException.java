package com.example.travelmanager.config.WebException;

import org.springframework.http.HttpStatus;

public class TravelControllerException extends ErrorException {

    private static final Integer TravelApplicationNotFound = 1001;
    public static final TravelControllerException TravelApplicationNotFoundException =
            new TravelControllerException(TravelApplicationNotFound, "TravelApplication not found", HttpStatus.NOT_FOUND);

    private static final Integer TravelApplicationForbidden= 1002;
    public static final TravelControllerException TravelApplicationForbiddenException =
            new TravelControllerException(TravelApplicationForbidden, "Not allowed to modify this application", HttpStatus.NOT_FOUND);

    // 构造函数
    private TravelControllerException(int code, String msg, HttpStatus httpStatus) {
        super(code, msg, httpStatus);
    }
}
