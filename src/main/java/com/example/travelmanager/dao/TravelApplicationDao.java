package com.example.travelmanager.dao;

import com.example.travelmanager.entity.TravelApplication;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TravelApplicationDao extends CrudRepository<TravelApplication, Integer> , JpaSpecificationExecutor<TravelApplication> {

}
