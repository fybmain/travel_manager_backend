package com.example.travelmanager.service.admin;

import com.example.travelmanager.payload.ApproveUserPayload;
import com.example.travelmanager.response.admin.UsersResponse;

public interface AdminService {
    UsersResponse getUsers(Boolean enable, Integer page, Integer size);

    void approveUser(ApproveUserPayload approveUserPaylod);
}