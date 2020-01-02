package com.example.travelmanager.service.TravelApplication;

import com.example.travelmanager.config.WebException.BadRequestException;
import com.example.travelmanager.config.WebException.TravelControllerException;
import com.example.travelmanager.dao.TravelApplicationDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.TravelApplication;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.ApplicationStatusEnum;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.TravelApplicationPayload;
import com.example.travelmanager.payload.TravelApprovalPayload;
import com.example.travelmanager.response.travel.TravelApplicationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TravelApplicationServiceImpl implements TravelApplicationService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private TravelApplicationDao travelApplicationDao;

    @Override
    public TravelApplicationsResponse getTravelApplications(int uid, int page, int size) {
        page = (page > 0) ? (page - 1) : 0;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<TravelApplication> applications = travelApplicationDao.findAll( (Specification<TravelApplication>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList();
            list.add(criteriaBuilder.equal(root.get("applicantId").as(Integer.class), uid));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);

        // response: 返回值
        TravelApplicationsResponse response = new TravelApplicationsResponse();

        response.setItems(new ArrayList<TravelApplication>());
        response.getItems().addAll(applications.toList());

        // 设置总数
        response.setTotal((int) applications.getTotalElements());

        return response;
    }

    @Override
    public TravelApplication getTravelApplication(int uid, int applyId) {
        User user = userDao.findById(uid).get();
        var query = travelApplicationDao.findById(applyId);
        if (query.isEmpty()) {
            throw TravelControllerException.TravelApplicationNotFoundException;
        }
        TravelApplication travelApplication = query.get();
        if (user.getRole() == UserRoleEnum.Employee.getRoleId()) {
            if (travelApplication.getApplicantId() != user.getId()) {
                throw TravelControllerException.TravelApplicationForbiddenException;
            }
        }
        else if (user.getRole() == UserRoleEnum.DepartmentManager.getRoleId()) {
            if (travelApplication.getDepartmentId() != user.getDepartmentId()) {
                throw TravelControllerException.TravelApplicationForbiddenException;
            }
        }
        return travelApplication;
    }

    @Override
    public void travelApproval(int uid,TravelApprovalPayload travelApprovalPayload) {
        User user = userDao.findById(uid).get();
        var query = travelApplicationDao.findById(travelApprovalPayload.getApplyId());
        if (query.isEmpty()) {
            throw TravelControllerException.TravelApplicationNotFoundException;
        }
        TravelApplication travelApplication = query.get();
        if (travelApplication.getStatus() == ApplicationStatusEnum.NeedDepartmentManagerApprove.getStatus()) {
            if (user.getRole() != UserRoleEnum.DepartmentManager.getRoleId() || user.getDepartmentId() != travelApplication.getDepartmentId()) {
                throw TravelControllerException.TravelApplicationForbiddenException;
            }
            if (travelApprovalPayload.getApproved() == true) {
                travelApplication.setStatus(ApplicationStatusEnum.NeedManagerApprove.getStatus());
            }
            else {
                travelApplication.setStatus(ApplicationStatusEnum.ApplicationNotApproved.getStatus());
            }
            travelApplicationDao.save(travelApplication);
        }
        else if (travelApplication.getStatus() == ApplicationStatusEnum.NeedManagerApprove.getStatus()) {
            if (user.getRole() != UserRoleEnum.Manager.getRoleId()) {
                throw TravelControllerException.TravelApplicationForbiddenException;
            }
            if (travelApprovalPayload.getApproved() == true) {
                travelApplication.setStatus(ApplicationStatusEnum.ApplicationApproved.getStatus());
            }
            else {
                travelApplication.setStatus(ApplicationStatusEnum.ApplicationNotApproved.getStatus());
            }
            travelApplicationDao.save(travelApplication);
        }
    }

    @Override
    public void travelApply(int uid, TravelApplicationPayload travelApplicationPayload) {
        User user = userDao.findById(uid).get();
        TravelApplication travelApplication = new TravelApplication();

        travelApplication.setApplicantId(uid);

        travelApplication.setReason(travelApplicationPayload.getReason());
        travelApplication.setStartTime(travelApplicationPayload.getStartTime());
        travelApplication.setEndTime(travelApplicationPayload.getEndTime());
        travelApplication.setProvince(travelApplicationPayload.getProvince());
        travelApplication.setCity(travelApplicationPayload.getCity());
        travelApplication.setDepartmentId(user.getDepartmentId());
        travelApplication.setFoodBudget(travelApplicationPayload.getBudget().getFood());
        travelApplication.setHotelBudget(travelApplicationPayload.getBudget().getHotel());
        travelApplication.setVehicleBudget(travelApplicationPayload.getBudget().getVehicle());
        travelApplication.setOtherBudget(travelApplicationPayload.getBudget().getOther());

        if (user.getDepartmentId() == null) {
            throw new BadRequestException("employee should belong to a department");
        }
        travelApplication.setDepartmentId(user.getDepartmentId());
        travelApplication.setPaid(false);
        travelApplication.setApplyTime(new Date());

        if (user.getRole() == UserRoleEnum.DepartmentManager.getRoleId()){
            travelApplication.setStatus(ApplicationStatusEnum.NeedManagerApprove.getStatus());
        }
        else if (user.getRole() == UserRoleEnum.Manager.getRoleId()) {
            travelApplication.setStatus(ApplicationStatusEnum.ApplicationApproved.getStatus());
        }
        else {
            travelApplication.setStatus(ApplicationStatusEnum.NeedDepartmentManagerApprove.getStatus());
        }

        travelApplicationDao.save(travelApplication);
    }
}
