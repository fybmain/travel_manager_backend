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
        new AdminControllerException(userCanNotChange,userCanNotChangeMsg, HttpStatus.BAD_REQUEST);

    private static final Integer departmentNotExists = 1003;
    private static final String departmentNotExistseMsg = "部门不存在";
    public static final AdminControllerException departmentNotExistsException =
        new AdminControllerException(departmentNotExists,departmentNotExistseMsg, HttpStatus.NOT_FOUND);
    
    private static final Integer userShouldBelongToADepartment = 1004;
    private static final String userShouldBelongToADepartmentMsg = "用户必须属于一个部门";
    public static final AdminControllerException userShouldBelongToADepartmentException =
        new AdminControllerException(userShouldBelongToADepartment,userShouldBelongToADepartmentMsg, HttpStatus.BAD_REQUEST);

    private static final Integer managerExist = 1005;
    private static final String managerExistMsg = "该部门经理已存在";
    public static final AdminControllerException managerExistException =
        new AdminControllerException(managerExist,managerExistMsg, HttpStatus.BAD_REQUEST);

    // 构造函数
    private AdminControllerException(int code, String msg, HttpStatus httpStatus) {
        super(code, msg, httpStatus);
    }
}
