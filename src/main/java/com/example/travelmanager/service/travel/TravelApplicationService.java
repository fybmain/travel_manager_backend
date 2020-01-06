package com.example.travelmanager.service.travel;

import com.example.travelmanager.payload.TravelApplicationPayload;

import java.util.List;

import com.example.travelmanager.payload.ApprovalPayload;
import com.example.travelmanager.response.travel.DetailTravelApplication;
import com.example.travelmanager.response.travel.ProvinceAndTimesResponse;
import com.example.travelmanager.response.travel.TravelApplicationsResponse;

public interface TravelApplicationService {
    void travelApply(int uid, TravelApplicationPayload travelApplicationPayload);

    void travelApproval(int uid, ApprovalPayload approvalPayload);

    DetailTravelApplication getTravelApplication(int uid, int applyId);

    TravelApplicationsResponse getTravelApplications(int uid, int page, int size, String state);

    TravelApplicationsResponse getTravelApplicationsByDepartmentId(int uid, int page, int size, String state, int departmentId);

    TravelApplicationsResponse getTravelUnpaidApplication(int uid, int page, int size);

    List<ProvinceAndTimesResponse> getTravelTimes(Integer uid, Integer departmentId, String startTime, String endTime);
}
