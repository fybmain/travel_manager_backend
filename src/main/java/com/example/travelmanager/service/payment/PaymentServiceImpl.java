package com.example.travelmanager.service.payment;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

// TODO: add exception for some condition
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
            // TODO@@
            throw new Exception();
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
}
