package com.example.travelmanager.service.admin;

import com.example.travelmanager.payload.ApproveUserPayload;
import com.example.travelmanager.payload.ChangeDepartmentPayload;
import com.example.travelmanager.payload.ChangeUserRolePayload;
import com.example.travelmanager.response.admin.UsersResponse;

public interface AdminService {
    UsersResponse getUsers(Boolean enable, Integer page, Integer size);

    void approveUser(ApproveUserPayload approveUserPaylod);

    void changeUserRole(ChangeUserRolePayload changeUserRolePayload);

    void changeDepartment(ChangeDepartmentPayload changeDepartmentPayload);
}