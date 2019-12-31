package com.example.travelmanager.enums;


public enum ApplicationStatusEnum {
    NeedDepartmentManagerApprove(1),
    NeedManagerApprove(2),
    ApplicationApproved(3),
    ApplicationNotApproved(4);


    int status;

    ApplicationStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
