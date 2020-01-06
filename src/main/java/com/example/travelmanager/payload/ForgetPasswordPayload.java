package com.example.travelmanager.payload;

import javax.validation.constraints.NotBlank;

import lombok.*;

public class ForgetPasswordPayload {
    @Getter @Setter
    @NotBlank
    private String workId;

    @Getter @Setter
    @NotBlank
    private String email;
}