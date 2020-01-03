package com.example.travelmanager.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.PaymentApplication;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PaymentApplicationDao extends CrudRepository<PaymentApplication, Integer>,
                                               JpaSpecificationExecutor<PaymentApplication> {

    @Query("select p from PaymentApplication p where p.applicantId = :uid and p.status in :statusSet")
    Page<PaymentApplication> findAllByApplicantId(@Param("uid") int uid, @Param("statusSet") Set<Integer>statusSet, Pageable pageable);

    @Query("select p from PaymentApplication p where p.departmentId = :departmentId and p.status in :statusSet")
    Page<PaymentApplication> findAllByDepartmentIdAndStatus(@Param("departmentId") int departmentId, @Param("statusSet") Set<Integer>statusSet, Pageable pageable);

    @Query("select p from PaymentApplication p where p.status in :statusSet")
    Page<PaymentApplication> findAllByStatus(@Param("statusSet") Set<Integer> set, Pageable pageable);

}
