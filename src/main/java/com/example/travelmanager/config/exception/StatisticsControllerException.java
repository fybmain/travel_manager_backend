package com.example.travelmanager.config.exception;

import org.springframework.http.HttpStatus;

public class StatisticsControllerException extends ErrorException {
    private static final Integer UserNotFound = 1001;
    public static final StatisticsControllerException UserNotFoundException = new StatisticsControllerException(UserNotFound, "request user not found in database", HttpStatus.NOT_FOUND);

    private static final Integer DepartmentNotFound = 1002;
    public static final StatisticsControllerException DepartmentNotFoundException = new StatisticsControllerException(DepartmentNotFound, "request department not found in database", HttpStatus.NOT_FOUND);

    private static final Integer PermissionDenied = 1003;
    public static final StatisticsControllerException PermissionDeniedException = new StatisticsControllerException(PermissionDenied, "user don't have enough permission to request this API", HttpStatus.FORBIDDEN);

    private static final Integer DateStringFormatError= 1004;
    public static final StatisticsControllerException DateStringFormatErrorException =
            new StatisticsControllerException(DateStringFormatError, "日期字符串格式错误，正确格式：yyyy-MM 如：2020-01", HttpStatus.BAD_REQUEST);

    private static final Integer YearError = 2001;
    public static final StatisticsControllerException YearErrorException = new StatisticsControllerException(YearError, "year should > 2000 and < 2020", HttpStatus.BAD_REQUEST);

    private static final Integer MonthError = 2002;
    public static final StatisticsControllerException MonthErrorException = new StatisticsControllerException(MonthError, "month number error", HttpStatus.BAD_REQUEST);

    private static final Integer TableNameError = 2003;
    public static final StatisticsControllerException TableNameErrorException = new StatisticsControllerException(TableNameError, "table name error", HttpStatus.INTERNAL_SERVER_ERROR);

    private static final Integer SQLError = 2004;
    public static final StatisticsControllerException SQLErrorException = new StatisticsControllerException(SQLError, "sql execute error", HttpStatus.INTERNAL_SERVER_ERROR);
    // 构造函数
    private StatisticsControllerException(int code, String msg, HttpStatus httpStatus) {
        super(code, msg, httpStatus);
    }
}
