package com.example.travelmanager.response.admin;

import com.example.travelmanager.entity.User;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Data
public class DetailUser extends User {

    @Getter @Setter
    private String departmentName;
}