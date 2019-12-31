package com.example.travelmanager.service.auth;

import com.example.travelmanager.entity.User;
import lombok.Getter;
import lombok.Setter;

public class UserInfo {
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String telephone;

    @Getter @Setter
    private Integer role;

    @Getter @Setter
    private Integer departmentId;

    public UserInfo() { }

    public UserInfo(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.role = user.getRole();
        this.departmentId = user.getDepartmentId();
    }
}
