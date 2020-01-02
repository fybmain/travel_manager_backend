package com.example.travelmanager.service.TravelApplication;

import com.example.travelmanager.config.WebException.BadRequestException;
import com.example.travelmanager.config.WebException.ForbiddenException;
import com.example.travelmanager.config.WebException.TravelControllerException;
import com.example.travelmanager.dao.TravelApplicationDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.TravelApplication;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.ApplicationStatusEnum;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.TravelApplicationPayload;
import com.example.travelmanager.payload.TravelApprovalPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TravelApplicationServiceImpl implements TravelApplicationService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private TravelApplicationDao travelApplicationDao;

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
