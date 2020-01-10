package com.example.travelmanager.response.homepage;

import com.example.travelmanager.response.DoubleSerialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ApplicationInfoHomePage {
    @Getter @Setter
    private Integer applyId;

    @Getter
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date applyTime;

    @Getter @Setter
    private Integer status;

    @JsonSerialize(using = DoubleSerialize.class)
    @Getter @Setter
    public Double money;
}
