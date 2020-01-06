package com.example.travelmanager.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SetPasswordPayload {
    @Getter @Setter
    private String oldPassword;

    @Getter @Setter
    private String newPassword;
}