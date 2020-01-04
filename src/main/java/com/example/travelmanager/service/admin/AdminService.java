package com.example.travelmanager.service.admin;

import com.example.travelmanager.response.admin.UsersResponse;

public interface AdminService {
    UsersResponse getUsers(Integer page, Integer size);
}