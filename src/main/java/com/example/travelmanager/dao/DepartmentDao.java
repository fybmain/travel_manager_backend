package com.example.travelmanager.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import com.example.travelmanager.entity.Department;

public interface DepartmentDao extends CrudRepository<Department, Integer> {
    Optional<Department> findByName(String name);
}
