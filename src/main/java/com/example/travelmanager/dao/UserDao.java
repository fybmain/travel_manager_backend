package com.example.travelmanager.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.User;

public interface UserDao extends CrudRepository<User, Integer> {
    User findByWorkId(String workId);
}
