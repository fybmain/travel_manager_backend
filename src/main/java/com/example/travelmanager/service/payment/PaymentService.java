package com.example.travelmanager.service.payment;

import com.example.travelmanager.entity.PaymentApplication;
import com.example.travelmanager.payload.PaymentApplicationPayload;

public interface PaymentService {
    PaymentApplication createByPayload(PaymentApplicationPayload payload, Integer userId) throws Exception;
}
