package com.example.travelmanager.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.PaymentApplication;

public interface PaymentApplicationDao extends CrudRepository<PaymentApplication, Integer> {

}
