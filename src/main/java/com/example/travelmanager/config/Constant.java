package com.example.travelmanager.config;

import com.example.travelmanager.enums.ApplicationStatusEnum;
import com.example.travelmanager.enums.UserRoleEnum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constant {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 2 * 60 * 60;
    public static final String SIGNING_KEY = "PBKDF2WithHmacSH";   // mut be 16,24 or 32 bytes
    public static final String HEADER_STRING = "Authorization";

    private static final HashSet<Integer> APPLICATION_USER_UNFINISHED = new HashSet<Integer>(Arrays.asList(
            ApplicationStatusEnum.NeedDepartmentManagerApprove.getStatus(),
            ApplicationStatusEnum.NeedManagerApprove.getStatus())
    );

    private static final HashSet<Integer> APPLICATION_USER_FINISHED = new HashSet<Integer>(Arrays.asList(
            ApplicationStatusEnum.ApplicationApproved.getStatus(),
            ApplicationStatusEnum.DepartmentManagerNotApproved.getStatus(),
            ApplicationStatusEnum.ManagerNotApproved.getStatus()
    ));
    
    private static final HashSet<Integer> APPLICATION_DEPARTMENT_MANAGER_UNFINISHED = new HashSet<Integer>(Arrays.asList(
            ApplicationStatusEnum.NeedDepartmentManagerApprove.getStatus()
    ));

    private static final HashSet<Integer> APPLICATION_DEPARTMENT_MANAGER_FINISHED = new HashSet<Integer>(Arrays.asList(
            ApplicationStatusEnum.ApplicationApproved.getStatus(),
            ApplicationStatusEnum.DepartmentManagerNotApproved.getStatus(),
            ApplicationStatusEnum.ManagerNotApproved.getStatus(),
            ApplicationStatusEnum.NeedManagerApprove.getStatus()
    ));

    private static final HashSet<Integer> APPLICATION_MANAGER_UNFINISHED = new HashSet<Integer>(Arrays.asList(
            ApplicationStatusEnum.NeedManagerApprove.getStatus()
    ));

    private static final HashSet<Integer> APPLICATION_MANAGER_FINISHED = new HashSet<Integer>(Arrays.asList(
            ApplicationStatusEnum.ApplicationApproved.getStatus(),
            ApplicationStatusEnum.ManagerNotApproved.getStatus()
    ));
        

    public static Set<Integer>getStatusSet(String state, int role) {
        Set<Integer> unfinishedSet = null;
        Set<Integer> finishedSet = null;
        Set<Integer> allSet = new HashSet<>();
        if (role == UserRoleEnum.Employee.getRoleId()) {
            unfinishedSet = APPLICATION_USER_UNFINISHED;
            finishedSet = APPLICATION_USER_FINISHED;
        }
        else if (role == UserRoleEnum.DepartmentManager.getRoleId()){
            unfinishedSet = APPLICATION_DEPARTMENT_MANAGER_UNFINISHED;
            finishedSet = APPLICATION_DEPARTMENT_MANAGER_FINISHED;
        }
        else if (role == UserRoleEnum.Manager.getRoleId()) {
            unfinishedSet = APPLICATION_MANAGER_UNFINISHED;
            finishedSet = APPLICATION_MANAGER_FINISHED;
        }

        if (finishedSet != null) {
            allSet.addAll(finishedSet);
        }

        if (unfinishedSet != null) {
            allSet.addAll(unfinishedSet);
        }

        if (state.equalsIgnoreCase("all")){
            return allSet;
        }
        else if (state.equalsIgnoreCase("finished")) {
            return finishedSet;
        }
        else if (state.equalsIgnoreCase("unfinished")) {
            return unfinishedSet;
        }
        return null;
    }
}
