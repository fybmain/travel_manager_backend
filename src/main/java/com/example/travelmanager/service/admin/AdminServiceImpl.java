package com.example.travelmanager.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.travelmanager.config.exception.AdminControllerException;
import com.example.travelmanager.dao.DepartmentDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.Department;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.payload.ApproveUserPayload;
import com.example.travelmanager.response.admin.UsersResponse;
import com.example.travelmanager.response.admin.DetailUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public UsersResponse getUsers(Boolean enable, Integer page, Integer size) {
        page = (page > 0) ? (page - 1) : 0;
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<User> users = userDao.findAllUsersExceptAdmin(enable, pageable);

        Map<Integer, String> departmentMap = new HashMap<Integer, String>();
        for (Department department: departmentDao.findAll()) {
            departmentMap.put(department.getId(), department.getName());
        }

        UsersResponse usersResponse = new UsersResponse();
        List<DetailUser> detailUsers = new ArrayList<DetailUser>();
        for(User user : users) {
            DetailUser detailUser = new DetailUser();
            detailUser.setName(user.getName());
            detailUser.setDepartmentId(user.getDepartmentId());
            detailUser.setEmail(user.getEmail());
            detailUser.setId(user.getId());
            detailUser.setRole(user.getRole());
            detailUser.setStatus(user.getStatus());
            detailUser.setTelephone(user.getTelephone());
            detailUser.setWorkId(user.getWorkId());
            detailUser.setDepartmentName(departmentMap.getOrDefault(user.getDepartmentId(), "未知部门"));
            detailUsers.add(detailUser);
        }
        usersResponse.setDetailusers(detailUsers);
        usersResponse.setTotal((int)users.getTotalElements());

        return usersResponse;
    }

    @Override
    public void approveUser(ApproveUserPayload approveUserPaylod) {
        var userQuery = userDao.findById(approveUserPaylod.getUserId());
        if (userQuery.isEmpty()) {
            throw AdminControllerException.userNotExistException;
        }
        User user = userQuery.get();
        if (user.getStatus()) {
            throw AdminControllerException.userCanNotChangeException;
        }
        if (approveUserPaylod.getApproved()) {
            user.setStatus(true);
            userDao.save(user);
        }
        else {
            userDao.delete(user);
        }
    }
}