package com.example.travelmanager.response.statistics;

import com.example.travelmanager.response.DoubleSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.*;

public class DepartmentCost {
    @Getter @Setter
    public String departmentName;

    @Getter @Setter
    @JsonSerialize(using = DoubleSerialize.class)
    public Double cost;
}