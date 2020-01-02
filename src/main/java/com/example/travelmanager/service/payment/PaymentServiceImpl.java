package com.example.travelmanager.service.payment;

import com.alibaba.fastjson.JSON;
import com.example.travelmanager.config.WebException.PaymentControllerException;
import com.example.travelmanager.dao.PaymentApplicationDao;
import com.example.travelmanager.dao.PictureDao;
import com.example.travelmanager.dao.TravelApplicationDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.PaymentApplication;
import com.example.travelmanager.entity.Picture;
import com.example.travelmanager.entity.TravelApplication;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.ApplicationStatusEnum;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.PaymentApplicationPayload;
import com.example.travelmanager.response.payment.PaymentApplicationResponse;
import com.example.travelmanager.response.payment.SimplePayment;
import com.example.travelmanager.response.payment.SimplePaymentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TravelApplicationException 出差申请不存在
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentApplicationDao paymentApplicationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private TravelApplicationDao travelApplicationDao;

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
        if (travelApplicationDao.findById(payload.getTravelApplyId()).isEmpty()) {
            throw PaymentControllerException.TravelApplicationNotFoundException;
        }


        // get URLs of Picture by PictureId
        String URLStrings = "";
        ArrayList<Integer> pictureIds = payload.getPictureIds();
        for(Integer i : pictureIds) {
            Picture picture = pictureDao.findById(i).get();
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

        return paymentApplication;
    }

    public PaymentApplicationResponse getById(Integer Id) {

        var queryTemp = paymentApplicationDao.findById(Id);
        if(queryTemp.isEmpty()) {
            throw PaymentControllerException.PaymentApplicationNotFoundException;
        }
        PaymentApplication paymentApplication = queryTemp.get();

        // 找到对应用户
        var queryTemp2 = userDao.findById(paymentApplication.getApplicantId());
        if(queryTemp2.isEmpty()) {
            throw PaymentControllerException.UserNotFoundException;
        }
        User applicant = queryTemp2.get();

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

    // 仅显示当前用户的
    public SimplePaymentListResponse listCanPay(Integer userId, Integer pageSize, Integer pageNum) {
        User user = userDao.findById(userId).get();

        // TODO: 根据用户角色进行区分 当前为了测试分页查询只有当前用户的

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "id");

        // payments: 查询出来的paymentApplication集合. 其中的size为每一页的size，而非total
        Page<PaymentApplication> payments = paymentApplicationDao.findAll(new Specification<PaymentApplication>(){
            @Override
            public Predicate toPredicate(Root<PaymentApplication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                // 注意这里root.get的为类中的名字，而不是数据库的
                list.add(criteriaBuilder.equal(root.get("applicantId").as(Integer.class), userId));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        // response: 返回值
        SimplePaymentListResponse response = new SimplePaymentListResponse();
        response.setItems(new ArrayList<SimplePayment>());

        // 设置总数
        Long total = paymentApplicationDao.count();
        response.setTotal(total.intValue());

        for(var p:payments) {
            // 需要查询申请人名字
            var queryUserTemp = userDao.findById(p.getApplicantId());
            if(queryUserTemp.isEmpty()) {
                log.error("userId:" + p.getApplicantId() + " Not Found");
                throw PaymentControllerException.UserNotFoundException;
            }
            user = queryUserTemp.get();

            SimplePayment sp = new SimplePayment();
            sp.setApplyId(p.getId());
            sp.setApplicantName(user.getName());
            sp.setApplyTime(p.getApplyTime().toString());
            sp.setStatus(p.getStatus());

            response.getItems().add(sp);
        }

        return response;
    }
}
