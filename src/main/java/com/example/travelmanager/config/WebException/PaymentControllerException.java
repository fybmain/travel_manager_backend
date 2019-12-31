package com.example.travelmanager.config.WebException;

import org.springframework.http.HttpStatus;

public class PaymentControllerException extends ErrorException {

    public static final Integer TravelApplicationNotFound = 1001;
    public static final PaymentControllerException TravelApplicationNotFoundException = new PaymentControllerException(TravelApplicationNotFound, "The corresponding TravelApplication was not found", HttpStatus.NOT_FOUND);

    public static final Integer PaymentApplicationNotFound = 1002;
    public static final PaymentControllerException PaymentApplicationNotFoundException = new PaymentControllerException(PaymentApplicationNotFound, "The Payment Application that you queried not found", HttpStatus.NOT_FOUND);

    public static final Integer UserNotFound = 1003;
    public static final PaymentControllerException UserNotFoundException = new PaymentControllerException(UserNotFound, "The user of this payment application not found", HttpStatus.NOT_FOUND);

    public static final Integer ImageUploadFailed = 2001;
    public static final PaymentControllerException ImageUploadFailedException = new PaymentControllerException(ImageUploadFailed, "Picture uploaded failed", HttpStatus.INTERNAL_SERVER_ERROR);

    public static final Integer ImageGetFailed = 2002;
    public static final PaymentControllerException ImageGetFailedException = new PaymentControllerException(ImageGetFailed, "Picture get from frontend failed", HttpStatus.BAD_REQUEST);


    // 构造函数
    private PaymentControllerException(int code, String msg, HttpStatus httpStatus) {
        super(code, msg, httpStatus);
    }
}
