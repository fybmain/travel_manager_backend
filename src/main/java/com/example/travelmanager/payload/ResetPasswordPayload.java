package com.example.travelmanager.payload;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ResetPasswordPayload{
    @Getter @Setter
    private String oldPassword;

    @Getter @Setter
    private String newPassword;
}