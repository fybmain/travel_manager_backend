package com.example.travelmanager.config;

import com.example.travelmanager.config.WebException.TravelControllerException;
import com.example.travelmanager.enums.ApplicationStatusEnum;

import java.util.Arrays;
import java.util.HashSet;

public class Constant {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 2 * 60 * 60;
    public static final String SIGNING_KEY = "PBKDF2WithHmacSH";   // mut be 16,24 or 32 bytes
    public static final String HEADER_STRING = "Authorization";

    private static final HashSet<Integer> APPLICATION_UNFINISHED_STATE = new HashSet<Integer>(Arrays.asList(
            ApplicationStatusEnum.NeedDepartmentManagerApprove.getStatus(),
            ApplicationStatusEnum.NeedManagerApprove.getStatus())
    );

    private static final HashSet<Integer> APPLICATION_FINISHED_STATE = new HashSet<Integer>(Arrays.asList(
            ApplicationStatusEnum.ApplicationApproved.getStatus(),
            ApplicationStatusEnum.ApplicationNotApproved.getStatus()
    ));
    private static final HashSet<Integer> APPLICATION_ALL_STATE = new HashSet<Integer>(Arrays.asList(
            ApplicationStatusEnum.ApplicationNotApproved.getStatus(),
            ApplicationStatusEnum.ApplicationApproved.getStatus(),
            ApplicationStatusEnum.NeedManagerApprove.getStatus(),
            ApplicationStatusEnum.NeedDepartmentManagerApprove.getStatus()
    ));

    public static HashSet<Integer>getStatusSet(String state) {
        if (state.equalsIgnoreCase("all")){
            return Constant.APPLICATION_ALL_STATE;
        }
        else if (state.equalsIgnoreCase("finished")) {
            return Constant.APPLICATION_FINISHED_STATE;
        }
        else if (state.equalsIgnoreCase("unfinished")) {
            return Constant.APPLICATION_UNFINISHED_STATE;
        }

        return null;
    }
}
