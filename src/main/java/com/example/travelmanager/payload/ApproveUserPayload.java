package com.example.travelmanager.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ApproveUserPayload{
    @Getter @Setter
    @NotNull(message = "userId should not be null")
    private Integer userId;

    @Getter @Setter
    @NotNull(message = "approved should not be null")
    private Boolean approved;
}