package com.example.travelmanager.dao;

import com.example.travelmanager.entity.PaymentApplication;
import com.example.travelmanager.entity.TravelApplication;
import com.example.travelmanager.response.statistics.MoneyDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StatisticsDao extends CrudRepository<PaymentApplication, Integer> {
    @Query(nativeQuery = true,
            value = "SELECT SUM(food_payment) AS money, CONCAT(YEAR(apply_time), '-', MONTH(apply_time)) AS yearmonth " +
            "FROM payment_application WHERE department_id = :departmentId GROUP BY yearmonth")
    List<?> listFoodMoneyDateOfPayment(@Param("departmentId") int departmentId);

}
