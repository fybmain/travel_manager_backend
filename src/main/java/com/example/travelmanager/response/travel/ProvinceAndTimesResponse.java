package com.example.travelmanager.response.travel;

import java.util.List;

import lombok.*;

@Data
public class ProvinceAndTimesResponse {
    @Getter @Setter
    private String province;

    @Getter @Setter
    private Integer count;

    @Getter @Setter
    private List<CityAndTimes> cityAndTimes;
}