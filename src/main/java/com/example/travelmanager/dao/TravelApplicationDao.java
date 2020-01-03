package com.example.travelmanager.dao;

import com.example.travelmanager.entity.TravelApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TravelApplicationDao extends CrudRepository<TravelApplication, Integer> , JpaSpecificationExecutor<TravelApplication> {
    @Query("select t from TravelApplication t where t.applicantId = :uid and t.status in :statusSet")
    Page<TravelApplication> finaAllByApplicantId(@Param("uid") int uid, @Param("statusSet") Set<Integer>statusSet, Pageable pageable);

    @Query("select t from TravelApplication t where t.departmentId = :departmentId and t.status in :statusSet")
    Page<TravelApplication> findAllByDepartmentIdAndStatus(@Param("departmentId") int departmentId, @Param("statusSet") Set<Integer>statusSet, Pageable pageable);

    @Query("select t from TravelApplication t where t.status in :statusSet")
    Page<TravelApplication> findAllByStatus(@Param("statusSet") Set<Integer>statusSet, Pageable pageable);

    @Query("select t from TravelApplication t where t.paid = :paid and t.applicantId = :uid")
    Page<TravelApplication> findAllUnpaid(@Param("paid") Boolean paid, @Param("uid") Integer uid, Pageable pageable);
}
