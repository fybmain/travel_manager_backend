package com.example.travelmanager.service.TravelApplication;

import com.example.travelmanager.payload.TravelApplicationPayload;
import com.example.travelmanager.payload.TravelApprovalPayload;

public interface TravelApplicationService {
    void travelApply(int uid, TravelApplicationPayload travelApplicationPayload);
    void travelApproval(int uid, TravelApprovalPayload travelApprovalPayload);
}
