package com.example.travelmanager.service.travel;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.config.exception.BadRequestException;
import com.example.travelmanager.config.exception.TravelControllerException;
import com.example.travelmanager.dao.DepartmentDao;
import com.example.travelmanager.dao.MessageDao;
import com.example.travelmanager.dao.TravelApplicationDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.Department;
import com.example.travelmanager.entity.Message;
import com.example.travelmanager.entity.TravelApplication;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.ApplicationStatusEnum;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.TravelApplicationPayload;
import com.example.travelmanager.payload.ApprovalPayload;
import com.example.travelmanager.response.travel.CityAndTimes;
import com.example.travelmanager.response.travel.DetailTravelApplication;
import com.example.travelmanager.response.travel.ProvinceAndTimesResponse;
import com.example.travelmanager.response.travel.SimpleTravelApplication;
import com.example.travelmanager.response.travel.TravelApplicationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TravelApplicationServiceImpl implements TravelApplicationService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private TravelApplicationDao travelApplicationDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    MessageDao messageDao;

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
        Set<Integer> statusSet = Constant.getStatusSet(state, user.getRole());
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
        User user = userDao.findById(uid).get();

        Set<Integer> statusSet = Constant.getStatusSet(state, UserRoleEnum.Employee.getRoleId());
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
            t.setDepartmentName(departmentMap.getOrDefault(travelApplication.getDepartmentId(), "未知部门"));
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
    public DetailTravelApplication getTravelApplication(int uid, int applyId) {
        User user = userDao.findById(uid).get();
        var query = travelApplicationDao.findById(applyId);
        if (query.isEmpty()) {
            throw TravelControllerException.TravelApplicationNotFoundException;
        }
        TravelApplication application = query.get();
        DetailTravelApplication detailTravelApplication = new DetailTravelApplication();

        detailTravelApplication.setApplicantId(application.getApplicantId());
        var userQuery = userDao.findById(application.getApplicantId());
        if (userQuery.isEmpty()){
            detailTravelApplication.setApplicantName("未知用户");
        }
        else {
            detailTravelApplication.setApplicantName(userQuery.get().getName());
        }

        detailTravelApplication.setDepartmentId(application.getDepartmentId());
        var departmentQuery = departmentDao.findById(application.getDepartmentId());
        if (departmentQuery.isEmpty()){
            detailTravelApplication.setDepartmentName("未知部门");
        }
        else {
            detailTravelApplication.setDepartmentName(departmentQuery.get().getName());
        }

        detailTravelApplication.setId(application.getId());
        
        detailTravelApplication.setApplyTime(application.getApplyTime());
        detailTravelApplication.setStartTime(application.getStartTime());
        detailTravelApplication.setEndTime(application.getEndTime());

        detailTravelApplication.setFoodBudget(application.getFoodBudget());
        detailTravelApplication.setHotelBudget(application.getHotelBudget());
        detailTravelApplication.setOtherBudget(application.getOtherBudget());
        detailTravelApplication.setVehicleBudget(application.getVehicleBudget());
        detailTravelApplication.setPaid(application.getPaid());

        detailTravelApplication.setReason(application.getReason());
        detailTravelApplication.setProvince(application.getProvince());
        detailTravelApplication.setCity(application.getCity());
        detailTravelApplication.setDetailAddress(application.getDetailAddress());

        detailTravelApplication.setStatus(application.getStatus());
        detailTravelApplication.setComment(application.getComment());


        TravelApplication travelApplication = query.get();
        if (user.getRole() == UserRoleEnum.Employee.getRoleId()) {
            if (!travelApplication.getApplicantId().equals(user.getId())) {
                throw TravelControllerException.TravelApplicationForbiddenException;
            }
        }
        else if (user.getRole() == UserRoleEnum.DepartmentManager.getRoleId()) {
            if (!travelApplication.getDepartmentId().equals(user.getDepartmentId())) {
                throw TravelControllerException.TravelApplicationForbiddenException;
            }
        }
        return detailTravelApplication;
    }

    @Override
    public void travelApproval(int uid, ApprovalPayload approvalPayload) {
        User user = userDao.findById(uid).get();
        var query = travelApplicationDao.findById(approvalPayload.getApplyId());
        if (query.isEmpty()) {
            throw TravelControllerException.TravelApplicationNotFoundException;
        }

        TravelApplication travelApplication = query.get();
        //set comment
        String comment = approvalPayload.getComment();
        travelApplication.setComment(comment != null? comment : "");
        if (travelApplication.getStatus() == ApplicationStatusEnum.NeedDepartmentManagerApprove.getStatus()) {
            if (user.getRole() != UserRoleEnum.DepartmentManager.getRoleId() || user.getDepartmentId() != travelApplication.getDepartmentId()) {
                throw TravelControllerException.TravelApplicationForbiddenException;
            }
            if (approvalPayload.getApproved() == true) {
                travelApplication.setStatus(ApplicationStatusEnum.NeedManagerApprove.getStatus());
                addMessage("部门经理", "同意", "出差", travelApplication.getApplicantId(), travelApplication.getId());
            }
            else {
                travelApplication.setStatus(ApplicationStatusEnum.DepartmentManagerNotApproved.getStatus());
                addMessage("部门经理", "拒绝", "出差", travelApplication.getApplicantId(), travelApplication.getId());
            }
            travelApplicationDao.save(travelApplication);
        }
        else if (travelApplication.getStatus() == ApplicationStatusEnum.NeedManagerApprove.getStatus()) {
            if (user.getRole() != UserRoleEnum.Manager.getRoleId()) {
                throw TravelControllerException.TravelApplicationForbiddenException;
            }
            if (approvalPayload.getApproved() == true) {
                travelApplication.setStatus(ApplicationStatusEnum.ApplicationApproved.getStatus());
                addMessage("经理", "同意", "出差", travelApplication.getApplicantId(), travelApplication.getId());
            }
            else {
                travelApplication.setStatus(ApplicationStatusEnum.ManagerNotApproved.getStatus());
                addMessage("经理", "拒绝", "出差", travelApplication.getApplicantId(), travelApplication.getId());
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
        travelApplication.setDetailAddress(travelApplicationPayload.getDetailAddress());
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

    @Override
    public List<ProvinceAndTimesResponse> getTravelTimes(Integer uid, Integer departmentId, String startTimeStr, String endTimeStr) {
        // get Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = simpleDateFormat.parse(startTimeStr);

            endTime = simpleDateFormat.parse(endTimeStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime); 
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); 
            endTime = calendar.getTime();
        }
        catch (Exception e){
            throw TravelControllerException.DateStringFormatErrorException;
        }
        
        // Get data from db
        User user = userDao.findById(uid).get();
        List<Object[]> objs = null;
        if (departmentId == -1 ) {// for all 
            objs = travelApplicationDao.getCityCount(startTime, endTime);
        }
        else {
            if (user.getRole() == UserRoleEnum.DepartmentManager.getRoleId()) {
                departmentId = user.getDepartmentId();
            }
            objs = travelApplicationDao.getCityCountByDepartment(departmentId, startTime, endTime);
        }

        Map<String, ProvinceAndTimesResponse> map = new HashMap<String, ProvinceAndTimesResponse> ();
        for (Object[] obj: objs) {
            String province = obj[0].toString();
            String city = obj[1].toString();
            Integer count = Integer.parseInt(obj[2].toString());
            if (!map.containsKey(province)) {
                ProvinceAndTimesResponse provinceAndTimesResponse = new ProvinceAndTimesResponse();
                provinceAndTimesResponse.setCityAndTimes(new ArrayList<CityAndTimes>());
                provinceAndTimesResponse.setCount(0);
                provinceAndTimesResponse.setProvince(province);
                map.put(province, provinceAndTimesResponse);
            }
            ProvinceAndTimesResponse provinceAndTimesResponse = map.get(province);
            CityAndTimes cityAndTimes = new CityAndTimes();
            cityAndTimes.setCity(city);
            cityAndTimes.setCount(count);

            provinceAndTimesResponse.getCityAndTimes().add(cityAndTimes);
            int tmp = provinceAndTimesResponse.getCount() + count;
            provinceAndTimesResponse.setCount(tmp);
        }

        // to list
        List<ProvinceAndTimesResponse> provinceAndTimesResponses = new ArrayList<ProvinceAndTimesResponse>();
        for(ProvinceAndTimesResponse provinceAndTimesResponse: map.values()) {
            provinceAndTimesResponses.add(provinceAndTimesResponse);
        }
        provinceAndTimesResponses.sort(Comparator.comparing(ProvinceAndTimesResponse::getCount).reversed());
        return provinceAndTimesResponses;
    }

    public Boolean addMessage(String checker, String action, String applyType, Integer receiverId, Integer resourceId) {
        Message message = new Message();
        message.setMessage(Message.messageGenerator(checker, action, applyType, resourceId));
        message.setReceiverId(receiverId);
        messageDao.save(message);
        return null;
    }
}
