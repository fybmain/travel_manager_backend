package com.example.travelmanager.service.TravelApplication;

import com.example.travelmanager.entity.TravelApplication;
import com.example.travelmanager.payload.TravelApplicationPayload;
import com.example.travelmanager.payload.ApprovalPayload;
import com.example.travelmanager.response.travel.TravelApplicationsResponse;

public interface TravelApplicationService {
    void travelApply(int uid, TravelApplicationPayload travelApplicationPayload);

    void travelApproval(int uid, ApprovalPayload approvalPayload);

    TravelApplication getTravelApplication(int uid, int applyId);

    TravelApplicationsResponse getTravelApplications(int uid, int page, int size, String state);

    TravelApplicationsResponse getTravelApplicationsByDepartmentId(int uid, int page, int size, String state, int departmentId);

    TravelApplicationsResponse getTravelUnpaidApplication(int uid, int page, int size);
}
