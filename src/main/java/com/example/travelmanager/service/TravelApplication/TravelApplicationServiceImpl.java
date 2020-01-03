package com.example.travelmanager.service.TravelApplication;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.config.WebException.BadRequestException;
import com.example.travelmanager.config.WebException.TravelControllerException;
import com.example.travelmanager.dao.DepartmentDao;
import com.example.travelmanager.dao.TravelApplicationDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.Department;
import com.example.travelmanager.entity.TravelApplication;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.ApplicationStatusEnum;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.TravelApplicationPayload;
import com.example.travelmanager.payload.ApprovalPayload;
import com.example.travelmanager.response.travel.SimpleTravelApplication;
import com.example.travelmanager.response.travel.TravelApplicationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TravelApplicationServiceImpl implements TravelApplicationService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private TravelApplicationDao travelApplicationDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public TravelApplicationsResponse getTravelUnpaidApplication(int uid, int page, int size) {
        page = (page > 0) ? (page - 1) : 0;
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<TravelApplication> travelApplications = travelApplicationDao.findAllUnpaid(uid, pageable);
        return pageApplications(travelApplications);
    }

    @Override
    public TravelApplicationsResponse getTravelApplicationsByDepartmentId(int uid, int page, int size, String state, int departmentId) {
        page = (page > 0) ? (page - 1) : 0;

        User user = userDao.findById(uid).get();

        // valid state
        Set<Integer> statusSet = Constant.getStatusSet(state);
        if(statusSet == null) {
            throw TravelControllerException.GetApplicationsStateErrorException;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<TravelApplication> travelApplications = null;
        if (user.getRole() == UserRoleEnum.Manager.getRoleId() && departmentId == -1) {
            travelApplications = travelApplicationDao.findAllByStatus(statusSet, pageable);
        }
        else {
            if (user.getRole() == UserRoleEnum.DepartmentManager.getRoleId()) {
                departmentId = user.getDepartmentId();
            }
            travelApplications = travelApplicationDao.findAllByDepartmentIdAndStatus(departmentId, statusSet, pageable);
        }

        return pageApplications(travelApplications);
    }

    @Override
    public TravelApplicationsResponse getTravelApplications(int uid, int page, int size, String state) {
        page = (page > 0) ? (page - 1) : 0;

        Set<Integer> statusSet = Constant.getStatusSet(state);
        if(statusSet == null) {
            throw TravelControllerException.GetApplicationsStateErrorException;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<TravelApplication> travelApplications = travelApplicationDao.finaAllByApplicantId(uid, statusSet, pageable);

        return pageApplications(travelApplications);
    }


    private TravelApplicationsResponse pageApplications(Page<TravelApplication> travelApplications) {

        Map<Integer, String> departmentMap = new HashMap<Integer, String>();
        for (Department department: departmentDao.findAll()) {
            departmentMap.put(department.getId(), department.getName());
        }

        TravelApplicationsResponse response = new TravelApplicationsResponse();

        List<SimpleTravelApplication> simpleTravelApplications = new ArrayList<SimpleTravelApplication>();
        for (TravelApplication travelApplication: travelApplications) {
            SimpleTravelApplication t = new SimpleTravelApplication();
            t.setApplyId(travelApplication.getId());
            t.setApplyTime(travelApplication.getApplyTime());
            t.setStatus(travelApplication.getStatus());
            t.setDepartmentName(departmentMap.getOrDefault(travelApplication.getDepartmentId(), "unknown department"));
            var queryUser = userDao.findById(travelApplication.getApplicantId());
            if (queryUser.isEmpty()) {
                t.setApplicantName("用户不存在");
            }
            else  {
                t.setApplicantName(queryUser.get().getName());
            }
            simpleTravelApplications.add(t);
        }
        response.setItems(simpleTravelApplications);
        response.setTotal((int)travelApplications.getTotalElements());
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
    public void travelApproval(int uid, ApprovalPayload approvalPayload) {
        User user = userDao.findById(uid).get();
        var query = travelApplicationDao.findById(approvalPayload.getApplyId());
        if (query.isEmpty()) {
            throw TravelControllerException.TravelApplicationNotFoundException;
        }
        TravelApplication travelApplication = query.get();
        if (travelApplication.getStatus() == ApplicationStatusEnum.NeedDepartmentManagerApprove.getStatus()) {
            if (user.getRole() != UserRoleEnum.DepartmentManager.getRoleId() || user.getDepartmentId() != travelApplication.getDepartmentId()) {
                throw TravelControllerException.TravelApplicationForbiddenException;
            }
            if (approvalPayload.getApproved() == true) {
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
            if (approvalPayload.getApproved() == true) {
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
