package com.example.travelmanager.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.PaymentApplication;

public interface PaymentApplicationDao extends CrudRepository<PaymentApplication, Integer>,
                                               JpaSpecificationExecutor<PaymentApplication> {

}
