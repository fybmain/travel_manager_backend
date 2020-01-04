package com.example.travelmanager.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.User;

public interface UserDao extends CrudRepository<User, Integer> {
    User findByWorkId(String workId);

    @Query("select t from User t where t.role != 3")
    Page<User> findAllUsersExceptAdmin(Pageable pageable);
}
