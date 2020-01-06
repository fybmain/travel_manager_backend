package com.example.travelmanager.dao;

import com.alibaba.fastjson.JSON;
import com.example.travelmanager.response.statistics.MoneyDatePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;


@Repository
public class StatisticsDao {

    @Autowired
    EntityManager em;

    public List<MoneyDatePair> listFoodMoneyDateOfPayment(int departmentId, String sumKey, String tableName) {
        String s = "";
        String sql = "";

        if(departmentId == -1) {
            s = "SELECT SUM(%s) AS money, " +
                    "CONCAT(YEAR(apply_time), '-', MONTH(apply_time)) AS yearmonth " +
                    "FROM (%s) GROUP BY yearmonth";
            sql = String.format(s, sumKey, tableName);
        } else {
            s =  "SELECT SUM(%s) AS money, " +
                    "CONCAT(YEAR(apply_time), '-', MONTH(apply_time)) AS yearmonth " +
                    "FROM (%s) WHERE department_id = %d GROUP BY yearmonth";
            sql = String.format(s, sumKey, tableName, departmentId);
        }

        List<MoneyDatePair> moneyDatePairs = new ArrayList<MoneyDatePair>();
        List<Object[]> objects = em.createNativeQuery(sql).getResultList();

        for(var o:objects) {
            System.out.print(o[0]);
            MoneyDatePair pair = new MoneyDatePair((Double) o[0], (String) o[1]);
            moneyDatePairs.add(pair);
        }

        return moneyDatePairs;
    }
}