package com.example.travelmanager.dao;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import com.example.travelmanager.entity.PaymentApplication;
import com.example.travelmanager.entity.TravelApplication;
import com.example.travelmanager.response.statistics.DepartmentCost;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface StatisticsDao extends CrudRepository<PaymentApplication, Integer>{

    @Query("select d.name as departmentName, SUM(p.hotelPayment)+SUM(p.vehiclePayment)+SUM(p.foodPayment)+SUM(p.otherPayment) as cost from TravelApplication t, PaymentApplication p, Department d where t.id = p.travelId and d.id = t.departmentId and t.startTime >= :startTime and t.startTime < :endTime group by t.departmentId")
    List<Object []> getDpartmentCost(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
    
