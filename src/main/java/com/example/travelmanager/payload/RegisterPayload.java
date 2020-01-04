package com.example.travelmanager.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterPayload {
    @Getter
    @Setter
    @ApiModelProperty
    @NotBlank(message = "name should not be empty")
    private String name;

    @Getter
    @Setter
    @ApiModelProperty
    @NotBlank(message = "password should not be empty")
    private String password;

    @Getter
    @Setter
    @ApiModelProperty
    @NotBlank(message = "workId should not be empty")
    private String workId;

    @Getter
    @Setter
    @ApiModelProperty
    @NotBlank(message = "telephone should not be empty")
    private String telephone;

    @Getter
    @Setter
    @ApiModelProperty
    @NotBlank(message = "email should not be empty")
    private String email;
}
