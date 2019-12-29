package com.example.travelmanager.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.TravelApplication;

public interface TravelApplicationDao extends CrudRepository<TravelApplication, Integer> {

}
