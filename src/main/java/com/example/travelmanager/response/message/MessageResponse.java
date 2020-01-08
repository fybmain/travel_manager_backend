package com.example.travelmanager.response.message;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class MessageResponse {
    @Getter @Setter
    private ArrayList<SimpleMessage> messages;

    public MessageResponse() {
        this.messages = new ArrayList<SimpleMessage>();
    }
}
