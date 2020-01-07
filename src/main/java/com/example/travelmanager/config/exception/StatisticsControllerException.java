package com.example.travelmanager.config.exception;

import org.springframework.http.HttpStatus;

public class StatisticsControllerException extends ErrorException {
    private static final Integer UserNotFound = 1001;
    public static final StatisticsControllerException UserNotFoundException = new StatisticsControllerException(UserNotFound, "request user not found in database", HttpStatus.NOT_FOUND);

    private static final Integer DepartmentNotFound = 1002;
    public static final StatisticsControllerException DepartmentNotFoundException = new StatisticsControllerException(DepartmentNotFound, "request department not found in database", HttpStatus.NOT_FOUND);

    private static final Integer PermissionDenied = 1003;
    public static final StatisticsControllerException PermissionDeniedException = new StatisticsControllerException(PermissionDenied, "user don't have enough permission to request this API", HttpStatus.FORBIDDEN);

    private static final Integer TableNameError = 2003;
    public static final StatisticsControllerException TableNameErrorException = new StatisticsControllerException(TableNameError, "table name error", HttpStatus.BAD_REQUEST);
    // 构造函数
    private StatisticsControllerException(int code, String msg, HttpStatus httpStatus) {
        super(code, msg, httpStatus);
    }
}
