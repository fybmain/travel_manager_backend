package com.example.travelmanager.service.message;

import com.example.travelmanager.response.message.MessageResponse;
import org.springframework.stereotype.Service;


public interface MessageService {
    MessageResponse getMessages(Integer userId);
}
