package com.example.travelmanager.response.travel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TravelApplicationsResponse {
    @Getter @Setter
    private Integer total;

    @Getter @Setter
    private List<SimpleTravelApplication> items;
}
