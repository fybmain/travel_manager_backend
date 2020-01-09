package com.example.travelmanager.dao;

import com.example.travelmanager.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MessageDao extends CrudRepository<Message, Integer> {
    ArrayList<Message> findTop100AllByReceiverIdOrderByIdDesc(Integer receiverId);
}
