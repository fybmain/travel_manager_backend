package com.example.travelmanager.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@ApiModel(value = "Travel application payload")
public class TravelApplicationPayload {

    @Getter
    @Setter
    @ApiModelProperty
    @NotNull(message = "startTime should not be null")
    private Date startTime; //"2018-01-31T14:32:19Z",

    @Getter
    @Setter
    @ApiModelProperty
    @NotNull(message = "endTime should not be null")
    private Date endTime;   //2018-01-31T14:32:19Z",

    @Getter
    @Setter
    @ApiModelProperty
    @NotBlank(message = "province should not ne blank")
    private String province;

    @Getter
    @Setter
    @ApiModelProperty
    @NotBlank(message = "city should not ne blank")
    private String city;

    @Getter @Setter
    private String detailAddress;

    @Getter
    @Setter
    @ApiModelProperty
    private Budget budget;

    @Getter
    @Setter
    @ApiModelProperty
    @NotBlank(message = "reason should not be null")
    private String reason;

    public class Budget {
        @Getter
        @Setter
        @ApiModelProperty
        private float hotel;

        @Getter
        @Setter
        @ApiModelProperty
        private float vehicle;

        @Getter
        @Setter
        @ApiModelProperty
        private float food;

        @Getter
        @Setter
        @ApiModelProperty
        private float other;
    }

}
