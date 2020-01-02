package com.example.travelmanager.service.payment;

import com.example.travelmanager.entity.PaymentApplication;
import com.example.travelmanager.payload.PaymentApplicationPayload;
import com.example.travelmanager.response.payment.PaymentApplicationResponse;
import com.example.travelmanager.response.payment.SimplePaymentListResponse;

public interface PaymentService {
    PaymentApplication createByPayload(PaymentApplicationPayload payload, Integer userId);

    PaymentApplicationResponse getById(Integer Id);

    SimplePaymentListResponse listApplications(Integer userId, Integer pageSize, Integer pageNum, String state, Integer departmentId);

    SimplePaymentListResponse listMyApplications(Integer userId, Integer pageSize, Integer pageNum);
}
