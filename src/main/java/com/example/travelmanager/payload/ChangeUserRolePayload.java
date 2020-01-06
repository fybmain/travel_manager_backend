package com.example.travelmanager.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChangeUserRolePayload {
    // @Getter @Setter
    // private Integer departmentId;

    @Getter @Setter
    @NotNull
    private Integer roleId;

    @Getter @Setter 
    @NotNull
    private Integer userId;
}