package com.example.travelmanager.dao;

import com.example.travelmanager.entity.TravelApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface TravelApplicationDao extends CrudRepository<TravelApplication, Integer> , JpaSpecificationExecutor<TravelApplication> {
    @Query("select t from TravelApplication t where t.applicantId = :uid and t.status in :statusSet")
    Page<TravelApplication> finaAllByApplicantIdAndStatus(@Param("uid") int uid, @Param("statusSet") Set<Integer>statusSet, Pageable pageable);

    @Query("select t from TravelApplication t where t.departmentId = :departmentId and t.status in :statusSet")
    Page<TravelApplication> findAllByDepartmentIdAndStatus(@Param("departmentId") int departmentId, @Param("statusSet") Set<Integer>statusSet, Pageable pageable);

    @Query("select t from TravelApplication t where t.status in :statusSet")
    Page<TravelApplication> findAllByStatus(@Param("statusSet") Set<Integer>statusSet, Pageable pageable);

    @Query("select t from TravelApplication t where t.paid = false and t.applicantId = :uid and t.status = 3")
    Page<TravelApplication> findAllUnpaid(@Param("uid") Integer uid, Pageable pageable);

    @Query("select t.province, t.city, COUNT(t.id) from TravelApplication t  where t.status =3 and t.startTime >= :startDate and t.startTime < :endDate group by t.province, t.city ")
    List<Object[]> getCityCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select t.province, t.city, COUNT(t.id) from TravelApplication t where t.status =3 and t.startTime >= :startDate and t.startTime < :endDate and t.departmentId = :departmentId group by t.province, t.city")
    List<Object[]> getCityCountByDepartment(@Param("departmentId") Integer departmentId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
