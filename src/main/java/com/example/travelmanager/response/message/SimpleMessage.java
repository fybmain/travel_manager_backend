package com.example.travelmanager.response.message;

import lombok.Getter;
import lombok.Setter;

public class SimpleMessage {
    @Getter
    @Setter
    private String message;

    public SimpleMessage(String message) {
        this.message = message;
    }
}
