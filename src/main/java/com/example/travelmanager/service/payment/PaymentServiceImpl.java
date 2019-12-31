package com.example.travelmanager.service.payment;

import com.alibaba.fastjson.JSON;
import com.example.travelmanager.config.WebException.ErrorException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

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
    public PaymentApplication createByPayload(PaymentApplicationPayload payload, Integer userId) throws Exception {
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

    public PaymentApplicationResponse getById(Integer Id) throws Exception {
        if(paymentApplicationDao.findById(Id).isEmpty()) {
            throw PaymentControllerException.PaymentApplicationNotFoundException;
        }
        PaymentApplication paymentApplication = paymentApplicationDao.findById(Id).get();

        // 找到对应用户
        if(userDao.findById(paymentApplication.getApplicantId()).isEmpty()) {
            throw PaymentControllerException.UserNotFoundException;
        }
        User applicant = userDao.findById(paymentApplication.getApplicantId()).get();

        System.out.println(JSON.toJSONString(travelApplicationDao.findById(paymentApplication.getTravelId())));

        // 找到对应Travel
        if(travelApplicationDao.findById(paymentApplication.getTravelId()).isEmpty()) {
            throw PaymentControllerException.TravelApplicationNotFoundException;
        }
        TravelApplication travelApplication = travelApplicationDao.findById(paymentApplication.getTravelId()).get();

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
}
