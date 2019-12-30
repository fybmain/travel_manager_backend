package com.example.travelmanager.service.auth;

import com.example.travelmanager.entity.User;

public class UserInfo {
    private String name;

    private String email;

    private String telephone;

    private Integer role;

    private Integer departmentId;

    public UserInfo() { }

    public UserInfo(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.role = user.getRole();
        this.departmentId = user.getDepartmentId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
