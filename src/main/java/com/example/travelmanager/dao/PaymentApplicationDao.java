package com.example.travelmanager.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.PaymentApplication;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentApplicationDao extends CrudRepository<PaymentApplication, Integer>,
                                               JpaSpecificationExecutor<PaymentApplication> {

    // 获取需要检查的申请，且这个申请是自己发起的
    @Query(value = "select f from PaymentApplication f where f.applicantId = :userId and (f.status = 1 or f.status = 2)")
    Page<PaymentApplication> findNeedCheckByUserId(Integer userId, Pageable pageable);

    // 获取本部门需要检查的申请
    @Query(value = "select f from PaymentApplication f where f.departmentId = :departmentId and (f.status = 1 or f.status = 2)")
    Page<PaymentApplication> findNeedCheckByDepartmentId(Integer departmentId, Pageable pageable);

    // 获取全部需要检查的申请
    @Query(value = "select f from PaymentApplication f where (f.status = 1 or f.status = 2)")
    Page<PaymentApplication> findAllNeedCheck(Pageable pageable);
}
