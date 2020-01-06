package com.example.travelmanager.config.exception;

import org.springframework.http.HttpStatus;

public class AuthControllerException extends ErrorException {
    private static final long serialVersionUID = -1059113565501260579L;

    private static final Integer WorkIdExists = 1001;
    private static final String WorkIdExistsMsg = "work id exists";
    public static final AuthControllerException WorkIdExistException =
        new AuthControllerException(WorkIdExists,WorkIdExistsMsg, HttpStatus.BAD_REQUEST);


    private static final Integer OldPasswordError = 1002;
    private static final String OldPasswordErrorMsg = "密码错误";
    public static final AuthControllerException OldPasswordErrorException = 
        new AuthControllerException(OldPasswordError, OldPasswordErrorMsg, HttpStatus.BAD_REQUEST);

    private static final Integer workIdOrPasswordError = 1003;
    private static final String workIdOrPasswordErrorMsg = "用户名或密码错误";
    public static final AuthControllerException workIdOrPasswordErrorException = 
        new AuthControllerException(workIdOrPasswordError, workIdOrPasswordErrorMsg, HttpStatus.UNAUTHORIZED);
    
    private static final Integer notAllowedLoginError = 1004;
    private static final String notAllowedLoginErrorMsg = "用户不可登录";
    public static final AuthControllerException notAllowedLoginErrorException = 
        new AuthControllerException(notAllowedLoginError, notAllowedLoginErrorMsg, HttpStatus.LOCKED);

    private static final Integer emailError = 1005;
    private static final String emailErrorMsg = "邮箱错误";
    public static final AuthControllerException emailErrorException = 
        new AuthControllerException(emailError, emailErrorMsg, HttpStatus.BAD_REQUEST);

    private static final Integer userNotExistError = 1006;
    private static final String userNotExistErrorMsg = "用户不存在";
    public static final AuthControllerException userNotExistErrorException = 
        new AuthControllerException(userNotExistError, userNotExistErrorMsg, HttpStatus.NOT_FOUND);
    // 构造函数
    private AuthControllerException(int code, String msg, HttpStatus httpStatus) {
        super(code, msg, httpStatus);
    }
}
