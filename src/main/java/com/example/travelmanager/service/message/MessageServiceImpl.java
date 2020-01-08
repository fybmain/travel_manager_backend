package com.example.travelmanager.service.message;

import com.example.travelmanager.dao.MessageDao;
import com.example.travelmanager.entity.Message;
import com.example.travelmanager.response.message.MessageResponse;

import com.example.travelmanager.response.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public MessageResponse getMessages(Integer userId) {
        List<Message> messages = messageDao.findAllByReceiverId(userId);

        MessageResponse response = new MessageResponse();

        for(Message m:messages) {
            SimpleMessage simpleMessage = new SimpleMessage(m.getMessage());
            response.getMessages().add(simpleMessage);
        }
        return response;
    }


}
