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
    @Query("select t from TravelApplication t where t.applicantId = :uid")
    Page<TravelApplication> finaAllByApplicantId(@Param("uid") int uid, Pageable pageable);

    @Query("select t from TravelApplication t where t.applicantId = :uid and (t.status = 3 or t.status = 4)")
    Page<TravelApplication> finaAllByApplicantIdFinished(@Param("uid") int uid, Pageable pageable);

    @Query("select t from TravelApplication t where t.applicantId = :uid and (t.status = 1 or t.status = 2)")
    Page<TravelApplication> finaAllByApplicantIdUnFinished(@Param("uid") int uid, Pageable pageable);

    @Query("select t from TravelApplication t where t.status in :statusSet")
    Page<TravelApplication> findAllWithState(@Param("statusSet")Set<Integer> set, Pageable pageable);
}
