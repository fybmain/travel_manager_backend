package com.example.travelmanager.service.payment;

import com.example.travelmanager.entity.PaymentApplication;
import com.example.travelmanager.payload.PaymentApplicationPayload;
import com.example.travelmanager.response.payment.PaymentApplicationResponse;

public interface PaymentService {
    PaymentApplication createByPayload(PaymentApplicationPayload payload, Integer userId) throws Exception;

    PaymentApplicationResponse getById(Integer Id) throws Exception;
}
