package com.example.travelmanager.response.admin;

import java.util.List;

import com.example.travelmanager.response.admin.DetailUser;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Data
public class UsersResponse {

    @Getter @Setter 
    private Integer total;

    @Getter @Setter
    private List<DetailUser> detailusers;
}