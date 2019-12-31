package com.example.travelmanager.config.WebException;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class ErrorException extends Exception {
    @Getter @Setter
    private int code;

    @Getter @Setter
    private String message;

    @Getter @Setter
    private HttpStatus httpStatus;

    public ErrorException(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}


