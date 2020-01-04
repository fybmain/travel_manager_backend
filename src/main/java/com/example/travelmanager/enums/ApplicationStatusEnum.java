package com.example.travelmanager.enums;


public enum ApplicationStatusEnum {
    NeedDepartmentManagerApprove(1),
    NeedManagerApprove(2),
    ApplicationApproved(3),
    DepartmentManagerNotApproved(4),
    ManagerNotApproved(5);


    int status;

    ApplicationStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
