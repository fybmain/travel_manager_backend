package com.example.travelmanager.config.exception;

import org.springframework.http.HttpStatus;

public class PaymentControllerException extends ErrorException {

    private static final Integer TravelApplicationNotFound = 1001;
    public static final PaymentControllerException TravelApplicationNotFoundException = new PaymentControllerException(TravelApplicationNotFound, "The corresponding TravelApplication was not found", HttpStatus.NOT_FOUND);

    private static final Integer PaymentApplicationNotFound = 1002;
    public static final PaymentControllerException PaymentApplicationNotFoundException = new PaymentControllerException(PaymentApplicationNotFound, "PaymentApplication not found", HttpStatus.NOT_FOUND);

    private static final Integer UserNotFound = 1003;
    public static final PaymentControllerException UserNotFoundException = new PaymentControllerException(UserNotFound, "The user of this payment application not found", HttpStatus.NOT_FOUND);

    private static final Integer GetApplicationForbidden = 1004;
    public static final PaymentControllerException GetApplicationForbiddenException = new PaymentControllerException(GetApplicationForbidden, "User don't have access to this application", HttpStatus.FORBIDDEN);

    private static final Integer TravelApplicationNotApproved = 1005;
    public static final PaymentControllerException TravelApplicationNotApprovedException = new PaymentControllerException(TravelApplicationNotApproved, "TravelApplication not approved, can't add payment application", HttpStatus.FORBIDDEN);

    private static final Integer PictureNotFound = 1006;
    public static final PaymentControllerException PictureNotFoundException = new PaymentControllerException(PictureNotFound, "picture not found", HttpStatus.NOT_FOUND);

    private static final Integer ImageUploadFailed = 2001;
    public static final PaymentControllerException ImageUploadFailedException = new PaymentControllerException(ImageUploadFailed, "Picture uploaded failed", HttpStatus.INTERNAL_SERVER_ERROR);

    private static final Integer ImageGetFailed = 2002;
    public static final PaymentControllerException ImageGetFailedException = new PaymentControllerException(ImageGetFailed, "Picture get from frontend failed", HttpStatus.BAD_REQUEST);

    private static final Integer StateParamError = 3001;
    public static final PaymentControllerException StateParamErrorException = new PaymentControllerException(StateParamError, "state param can only be \"all\", \"finished\" and \"unfinished\" ", HttpStatus.BAD_REQUEST);

    private static final Integer DepartmentIdParamError = 3002;
    public static final PaymentControllerException DepartmentIdParamErrorException = new PaymentControllerException(DepartmentIdParamError, "departmentId should >= -1", HttpStatus.BAD_REQUEST);

    private static final Integer ApplicationStateCanNotModify = 4001;
    public static final PaymentControllerException ApplicationStateCanNotModifyException = new PaymentControllerException(ApplicationStateCanNotModify, "application state is approved OR unapproved, can't modify", HttpStatus.FORBIDDEN);

    private static final Integer ApplicationCanNotApprove = 4002;
    public static final PaymentControllerException ApplicationCanNotApproveException = new PaymentControllerException(ApplicationCanNotApprove, "you don't have enough privilege to approve this application now", HttpStatus.FORBIDDEN);

    private static final Integer DuplicatePaymentApplication = 4003;
    public static final PaymentControllerException DuplicatePaymentApplicationException = new PaymentControllerException(DuplicatePaymentApplication, "This travel application have ongoing payment application already", HttpStatus.FORBIDDEN);
    // 构造函数
    private PaymentControllerException(int code, String msg, HttpStatus httpStatus) {
        super(code, msg, httpStatus);
    }
}
