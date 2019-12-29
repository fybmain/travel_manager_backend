package com.example.travelmanager.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {


    User findByUsername(String username);
}
