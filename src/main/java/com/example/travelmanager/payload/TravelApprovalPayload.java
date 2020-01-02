package com.example.travelmanager.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
public class TravelApprovalPayload {
    @Getter @Setter
    @ApiModelProperty @NotNull
    private Boolean approved;

    @Getter @Setter
    @ApiModelProperty @NotNull
    private Integer applyId;
}
