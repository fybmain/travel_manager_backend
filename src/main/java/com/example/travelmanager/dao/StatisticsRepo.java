package com.example.travelmanager.dao;

import com.example.travelmanager.config.exception.StatisticsControllerException;
import com.example.travelmanager.response.statistics.MoneyDatePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;


@Repository
public class StatisticsRepo {

    @Autowired
    EntityManager em;

    public List<MoneyDatePair> listOneMoneyDatePair(int departmentId, String sumKey, String tableName) {
        String sql = "";

        if(departmentId == -1) {
            sql = "SELECT SUM(" + sumKey + ") AS money, " +
                "DATE_FORMAT(apply_time,'%Y-%m') AS yearmonth " +
                "FROM (" + tableName + ") GROUP BY yearmonth ORDER by yearmonth";
        } else {
            sql = "SELECT SUM(" + sumKey + ") AS money, " +
                "DATE_FORMAT(apply_time,'%Y-%m') AS yearmonth " +
                "FROM (" + tableName + ") WHERE department_id = " + departmentId +
                " GROUP BY yearmonth ORDER by yearmonth";
        }

        List<MoneyDatePair> moneyDatePairs = new ArrayList<MoneyDatePair>();
        List<Object[]> objects = em.createNativeQuery(sql).getResultList();

        for(var o:objects) {
            MoneyDatePair pair = new MoneyDatePair((Double) o[0], (String) o[1]);
            moneyDatePairs.add(pair);
        }

        return moneyDatePairs;
    }

    public List<MoneyDatePair> listAllMoneyDatePair(int departmentId, String tableName) {
        String sumString;
        if(tableName.contains("payment")) {
            sumString = "other_payment + food_payment + hotel_payment + vehicle_payment";
        } else if (tableName.contains("travel")){
            sumString = "other_budget + food_budget + hotel_budget + vehicle_budget";
        } else {
            throw StatisticsControllerException.TableNameErrorException;
        }

        String sql = "";
        if(departmentId == -1) {
            sql = "SELECT SUM(" + sumString + ") AS money, " +
                    "DATE_FORMAT(apply_time,'%Y-%m') AS yearmonth " +
                    "from (" + tableName + ") GROUP BY yearmonth ORDER BY yearmonth";
        } else {
            sql = "SELECT SUM(" + sumString + ") AS money, " +
                    "DATE_FORMAT(apply_time,'%Y-%m') AS yearmonth " +
                    "from (" + tableName + ") " + "WHERE department_id = " + departmentId +
                    " GROUP BY yearmonth ORDER BY yearmonth";
        }

        List<MoneyDatePair> moneyDatePairs = new ArrayList<MoneyDatePair>();
        List<Object[]> objects = em.createNativeQuery(sql).getResultList();

        for(var o:objects) {
            MoneyDatePair pair = new MoneyDatePair((Double) o[0], (String) o[1]);
            moneyDatePairs.add(pair);
        }

        return moneyDatePairs;
    }
}