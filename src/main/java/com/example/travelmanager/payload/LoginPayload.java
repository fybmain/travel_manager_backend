package com.example.travelmanager.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class LoginPayload {
    @Getter @Setter
    @ApiModelProperty
    @NotBlank(message = "workId should not be null")
    private String workId;

    @Setter @Getter
    @ApiModelProperty
    @NotBlank(message = "password should not be null")
    private String password;


}
