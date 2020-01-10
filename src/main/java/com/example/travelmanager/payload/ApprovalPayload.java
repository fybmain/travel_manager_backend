package com.example.travelmanager.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
public class ApprovalPayload {
    @Getter @Setter
    @NotNull
    private Boolean approved;

    @Getter @Setter
    @NotNull
    private Integer applyId;

    @Getter @Setter
    private String comment;
}
