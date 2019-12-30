package com.example.travelmanager.enums;

public enum UserRoleEnum {
    Employee (0),
    DepartmentManager(1),
    Manager(2),
    Admin(3);

    int roleId;

    UserRoleEnum(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }
}
