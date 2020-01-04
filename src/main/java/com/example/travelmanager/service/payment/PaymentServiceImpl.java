package com.example.travelmanager.service.payment;

import com.alibaba.fastjson.JSON;
import com.example.travelmanager.config.Constant;
import com.example.travelmanager.config.WebException.PaymentControllerException;
import com.example.travelmanager.dao.*;
import com.example.travelmanager.entity.*;
import com.example.travelmanager.enums.ApplicationStatusEnum;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.PaymentApplicationPayload;
import com.example.travelmanager.response.payment.PaymentApplicationResponse;
import com.example.travelmanager.response.payment.SimplePayment;
import com.example.travelmanager.response.payment.SimplePaymentListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

// TravelApplicationException 出差申请不存在
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentApplicationDao paymentApplicationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private TravelApplicationDao travelApplicationDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public PaymentApplication createByPayload(PaymentApplicationPayload payload, Integer userId) {
        PaymentApplication paymentApplication = new PaymentApplication();
        User user = userDao.findById(userId).get();

        paymentApplication.setApplicantId(userId);
        paymentApplication.setDepartmentId(user.getDepartmentId());
        paymentApplication.setApplyTime(new Date());

        if(user.getRole() == UserRoleEnum.Employee.getRoleId()) {
            paymentApplication.setStatus(ApplicationStatusEnum.NeedDepartmentManagerApprove.getStatus());
        }
        else if (user.getRole() == UserRoleEnum.DepartmentManager.getRoleId()) {
            paymentApplication.setStatus(ApplicationStatusEnum.NeedManagerApprove.getStatus());
        }
        else if (user.getRole() == UserRoleEnum.Manager.getRoleId()) {
            paymentApplication.setStatus(ApplicationStatusEnum.ApplicationApproved.getStatus());
        }

        paymentApplication.setTravelId(payload.getTravelApplyId());
        var querytravel = travelApplicationDao.findById(payload.getTravelApplyId());
        if(querytravel.isEmpty()) {
            throw PaymentControllerException.TravelApplicationNotFoundException;
        }

        var travelApplication = querytravel.get();
        // travel申请未通过
        if(travelApplication.getStatus() != ApplicationStatusEnum.ApplicationApproved.getStatus()) {
            throw PaymentControllerException.TravelApplicationNotApprovedException;
        }

        // 已经为true，说明该出差申请有其他报销申请，且还未通过，抛出异常
        if(travelApplication.getPaid()) {
            throw PaymentControllerException.DuplicatePaymentApplicationException;
        }


        // get URLs of Picture by PictureId
        String URLStrings = "";
        ArrayList<Integer> pictureIds = payload.getPictureIds();
        for(Integer i : pictureIds) {
            var pictureQuery = pictureDao.findById(i);
            if(pictureQuery.isEmpty()) {
                throw PaymentControllerException.PictureNotFoundException;
            }
            Picture picture = pictureQuery.get();
            URLStrings = URLStrings + picture.getUrl();
            // 不是最后一个元素，则加 " " 空格
            if(i != pictureIds.get(pictureIds.size()-1)) {
                URLStrings += " ";
            }
        }
        paymentApplication.setInvoiceURLs(URLStrings);

        paymentApplication.setHotelPayment(payload.getPayment().getHotel());
        paymentApplication.setFoodPayment(payload.getPayment().getFood());
        paymentApplication.setVehiclePayment(payload.getPayment().getVehicle());
        paymentApplication.setOtherPayment(payload.getPayment().getOther());

        System.out.println(paymentApplication.toString());

        paymentApplicationDao.save(paymentApplication);

        // 将travel 的paid 改为true
        travelApplication.setPaid(true);
        travelApplicationDao.save(travelApplication);

        return paymentApplication;
    }

    public PaymentApplicationResponse getById(Integer userId, Integer applicationId) {
        // 查询用户
        var userQuery = userDao.findById(userId);
        if(userQuery.isEmpty()) {
            throw PaymentControllerException.UserNotFoundException;
        }
        User uQuery = userQuery.get();


        var applicationQuery = paymentApplicationDao.findById(applicationId);
        if(applicationQuery.isEmpty()) {
            throw PaymentControllerException.PaymentApplicationNotFoundException;
        }
        PaymentApplication paymentApplication = applicationQuery.get();

        // 找到对应用户
        var queryTemp2 = userDao.findById(paymentApplication.getApplicantId());
        if(queryTemp2.isEmpty()) {
            throw PaymentControllerException.UserNotFoundException;
        }
        User applicant = queryTemp2.get();

        // 权限检查
        // 普通用户只看自己的
        if(uQuery.getRole() == UserRoleEnum.Employee.getRoleId()) {
            if(!uQuery.getId().equals(applicant.getId())) {
                throw PaymentControllerException.GetApplicationForbiddenException;
            }
        } else if(uQuery.getRole() == UserRoleEnum.DepartmentManager.getRoleId()) {
            // 部门经理看本部门的
            if(!uQuery.getDepartmentId().equals(applicant.getDepartmentId())) {
                throw PaymentControllerException.GetApplicationForbiddenException;
            }
        }
        // 经理全权限，不做筛查


        // 找到对应Travel
        var queryTemp3 = travelApplicationDao.findById(paymentApplication.getTravelId());
        if (queryTemp3.isEmpty()) {
            throw PaymentControllerException.TravelApplicationNotFoundException;
        }
        TravelApplication travelApplication = queryTemp3.get();


        PaymentApplicationResponse response = new PaymentApplicationResponse();
        response.setPayment(new PaymentApplicationResponse.Payment());
        response.setBudget(new PaymentApplicationResponse.Budget());

        response.setApplicant(applicant.getName());

        response.setPictureURLs(paymentApplication.getInvoiceURLs());

        response.setTravelApplyId(paymentApplication.getTravelId());


        response.getPayment().setFood(paymentApplication.getFoodPayment());
        response.getPayment().setHotel(paymentApplication.getHotelPayment());
        response.getPayment().setOther(paymentApplication.getOtherPayment());
        response.getPayment().setVehicle(paymentApplication.getVehiclePayment());

        response.getBudget().setFood(travelApplication.getFoodBudget());
        response.getBudget().setHotel(travelApplication.getHotelBudget());
        response.getBudget().setOther(travelApplication.getHotelBudget());
        response.getBudget().setVehicle(travelApplication.getVehicleBudget());

        return response;
    }

    public SimplePaymentListResponse listApplications(Integer userId, Integer pageSize, Integer pageNum, String state, Integer departmentId) {
        // 发起请求的用户
        final User user = userDao.findById(userId).get();
        
        // 1. 构建查询set. 根据用户请求参数不同，填充不同的申请到list中.
        Set<Integer> statusSet = Constant.getStatusSet(state, user.getRole());

        if(statusSet == null) {
            throw PaymentControllerException.StateParamErrorException;
        }

        // 2. departmentId < -1 抛出异常
        if(departmentId < -1) {
            throw PaymentControllerException.DepartmentIdParamErrorException;
        }

        // 分页相关实例
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<PaymentApplication> payments;

        //3. 判断用户角色 角色为部门经理返回部门的 角色为总经理按照departmentId设计返回
        if(user.getRole() == UserRoleEnum.DepartmentManager.getRoleId()) {
            // 3.1 部门经理先获取经理所属部门，且status相同的items
            payments = paymentApplicationDao.findAllByDepartmentIdAndStatus(user.getDepartmentId(), statusSet, pageable);
        } else {
            // 3.2 总经理先看传入的参数
            if(departmentId != -1) {
                payments = paymentApplicationDao.findAllByDepartmentIdAndStatus(departmentId, statusSet, pageable);
            }
            else {
                payments = paymentApplicationDao.findAllByStatus(statusSet, pageable);
            }
        }

        // response: 返回值
        SimplePaymentListResponse response = new SimplePaymentListResponse();
        response.setItems(new ArrayList<SimplePayment>());

        // 设置总数
        response.setTotal((int) payments.getTotalElements());

        for(var p:payments) {
            // 需要查询申请人名字
            var queryUserTemp = userDao.findById(p.getApplicantId());
            String username = "";
            if(queryUserTemp.isEmpty()) {
                username = "用户已删除";
            } else {
                User u = queryUserTemp.get();
                username = u.getName();
            }

            var queryDepartmentTemp = departmentDao.findById(p.getDepartmentId());
            String departmentName = "";
            if(queryDepartmentTemp.isEmpty()) {
                departmentName = "部门已删除";
            } else {
                Department department = queryDepartmentTemp.get();
                departmentName = department.getName();
            }

            SimplePayment sp = new SimplePayment();
            sp.setApplyId(p.getId());
            sp.setApplicantName(username);
            sp.setDepartmentName(departmentName);
            sp.setApplyTime(p.getApplyTime().toString());
            sp.setStatus(p.getStatus());

            response.getItems().add(sp);
        }

        return response;

    }

    public SimplePaymentListResponse listMyApplications(Integer userId, Integer pageSize, Integer pageNum, String state) {
        // 发起请求的用户
        final User user = userDao.findById(userId).get();
        
        // 1. 构建查询set. 根据用户请求参数不同，填充不同的申请到list中.
        Set<Integer> statusSet = Constant.getStatusSet(state, user.getRole());
        if(statusSet == null) {
            throw PaymentControllerException.StateParamErrorException;
        }

        // response初始化
        SimplePaymentListResponse response = new SimplePaymentListResponse();
        response.setItems(new ArrayList<SimplePayment>());

        // 分页相关实例
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");
        Page<PaymentApplication> payments;
        payments = paymentApplicationDao.findAllByApplicantId(userId, statusSet, pageable);

        // 设置总数
        response.setTotal((int) payments.getTotalElements());

        // 查询用户名和部门名
        var username = user.getName();

        for(var p:payments) {
            var queryDepartmentTemp = departmentDao.findById(p.getDepartmentId());
            String departmentName = "";
            if(queryDepartmentTemp.isEmpty()) {
                departmentName = "部门已删除";
            } else {
                Department department = queryDepartmentTemp.get();
                departmentName = department.getName();
            }

            SimplePayment sp = new SimplePayment();
            sp.setApplyId(p.getId());
            sp.setApplicantName(username);
            sp.setDepartmentName(departmentName);
            sp.setApplyTime(p.getApplyTime().toString());
            sp.setStatus(p.getStatus());

            response.getItems().add(sp);
        }

        return response;
    }

    public void approve(Integer userId, Integer applicationId, Boolean approved) {
        User u = userDao.findById(userId).get();

        var applicationQuery = paymentApplicationDao.findById(applicationId);
        if(applicationQuery.isEmpty()) {
            throw PaymentControllerException.PaymentApplicationNotFoundException;
        }
        PaymentApplication application = applicationQuery.get();

        // 查询对应travel，判断是否存在
        var travelQuery = travelApplicationDao.findById(application.getTravelId());
        if(travelQuery.isEmpty()) {
            throw PaymentControllerException.TravelApplicationNotFoundException;
        }

        // 状态为已经固定了
        if(application.getStatus() == ApplicationStatusEnum.ApplicationApproved.getStatus() || 
           application.getStatus() == ApplicationStatusEnum.DepartmentManagerNotApproved.getStatus() || 
           application.getStatus() == ApplicationStatusEnum.ManagerNotApproved.getStatus()){
            throw PaymentControllerException.ApplicationStateCanNotModifyException;
        }

        // 以下为approve情况
        if(application.getStatus() == ApplicationStatusEnum.NeedDepartmentManagerApprove.getStatus()) {
            if(u.getRole() == UserRoleEnum.DepartmentManager.getRoleId()) {
                if(approved){
                    application.setStatus(ApplicationStatusEnum.NeedManagerApprove.getStatus());
                }
                else {
                    application.setStatus(ApplicationStatusEnum.DepartmentManagerNotApproved.getStatus());
                }
                paymentApplicationDao.save(application);
            } else {
                throw PaymentControllerException.ApplicationCanNotApproveException;
            }
        }
        else if(application.getStatus() == ApplicationStatusEnum.NeedManagerApprove.getStatus()) {
            if(u.getRole() == UserRoleEnum.Manager.getRoleId()) {
                if(approved) {
                    application.setStatus(ApplicationStatusEnum.ApplicationApproved.getStatus());
                } else {
                    application.setStatus(ApplicationStatusEnum.ManagerNotApproved.getStatus());
                }
                paymentApplicationDao.save(application);
            } else {
                throw PaymentControllerException.ApplicationCanNotApproveException;
            }
        }

        // 申请未通过，设置travel的为false
        if(application.getStatus() == ApplicationStatusEnum.ManagerNotApproved.getStatus() || 
           application.getStatus() == ApplicationStatusEnum.DepartmentManagerNotApproved.getStatus() ){
            TravelApplication travelApplication = travelQuery.get();
            travelApplication.setPaid(false);
            travelApplicationDao.save(travelApplication);
        }

    }
}
