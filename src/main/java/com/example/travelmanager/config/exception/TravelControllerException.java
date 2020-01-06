package com.example.travelmanager.config.exception;

import org.springframework.http.HttpStatus;

public class TravelControllerException extends ErrorException {
    private static final long serialVersionUID = 7685786668852690078L;
    private static final Integer TravelApplicationNotFound = 1001;
    public static final TravelControllerException TravelApplicationNotFoundException =
        new TravelControllerException(TravelApplicationNotFound, "TravelApplication not found", HttpStatus.NOT_FOUND);

    private static final Integer TravelApplicationForbidden= 1002;
    public static final TravelControllerException TravelApplicationForbiddenException =
            new TravelControllerException(TravelApplicationForbidden, "Not allowed to access this application", HttpStatus.NOT_FOUND);

    private static final Integer GetApplicationsStateError= 1003;
    public static final TravelControllerException GetApplicationsStateErrorException =
            new TravelControllerException(GetApplicationsStateError, "state must be Finished, Unfinished or All", HttpStatus.BAD_REQUEST);

    // 构造函数
    private TravelControllerException(int code, String msg, HttpStatus httpStatus) {
        super(code, msg, httpStatus);
    }
}
