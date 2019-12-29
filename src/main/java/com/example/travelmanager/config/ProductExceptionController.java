package com.example.travelmanager.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.travelmanager.service.auth.AuthException.*;

@ControllerAdvice
public class ProductExceptionController {
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<Object> exception(UnauthorizedException exception) {
        return new ResponseEntity<>("unauthorized", HttpStatus.UNAUTHORIZED);
    }

}