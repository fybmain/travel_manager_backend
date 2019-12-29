package com.example.travelmanager.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.Department;

public interface DepartmentDao extends CrudRepository<Department, Integer> {

}
