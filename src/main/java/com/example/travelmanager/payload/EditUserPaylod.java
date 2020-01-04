package com.example.travelmanager.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EditUserPaylod {
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String telephone;

    @Getter @Setter
    private String email;
}