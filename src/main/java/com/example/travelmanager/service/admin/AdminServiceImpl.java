package com.example.travelmanager.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.config.exception.AdminControllerException;
import com.example.travelmanager.dao.DepartmentDao;
import com.example.travelmanager.dao.UserDao;
import com.example.travelmanager.entity.Department;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.enums.UserRoleEnum;
import com.example.travelmanager.payload.ApproveUserPayload;
import com.example.travelmanager.payload.ChangeDepartmentPayload;
import com.example.travelmanager.payload.ChangeUserRolePayload;
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

    @Override
    public void changeUserRole(ChangeUserRolePayload changeUserRolePayload){
        // 获取用户
        var userQuery = userDao.findById(changeUserRolePayload.getUserId());
        if (userQuery.isEmpty()) {
            throw AdminControllerException.userNotExistException;
        }
        User user = userQuery.get();

        // 总经理
        if (changeUserRolePayload.getRoleId() == UserRoleEnum.Manager.getRoleId()) {
            // 如果总裁办不存在，添加总裁办
            var managerDepartmentQuery = departmentDao.findByName(Constant.managerDepartmentName);
            if (managerDepartmentQuery.isEmpty()) {
                releaseUserDepartment(user);
                Department department = new Department();
                department.setManagerId(user.getId());
                department.setName(Constant.managerDepartmentName);
                department = departmentDao.save(department);
                user.setDepartmentId(department.getId());
            }
            else {
                Department department = managerDepartmentQuery.get();
                if (department.getManagerId() != null) {
                    throw AdminControllerException.managerExistException;
                }
                releaseUserDepartment(user);
                department.setManagerId(user.getId());
                departmentDao.save(department);
                user.setDepartmentId(department.getId());
            }
            user.setRole(UserRoleEnum.Manager.getRoleId());
        }
        else {
            if (user.getDepartmentId() == null) {
                throw AdminControllerException.userShouldBelongToADepartmentException;
            }

            var departmentQuery = departmentDao.findById(user.getDepartmentId());
            if (departmentQuery.isEmpty()) {
                throw AdminControllerException.userShouldBelongToADepartmentException;
            }

            Department department = departmentQuery.get();

            if (changeUserRolePayload.getRoleId() == UserRoleEnum.DepartmentManager.getRoleId()) {
                // 部门经理已存在
                if (department.getManagerId() != null) {
                    throw AdminControllerException.managerExistException;
                }
                department.setManagerId(user.getId());
                departmentDao.save(department);

                user.setRole(UserRoleEnum.DepartmentManager.getRoleId());
            }
            else if (changeUserRolePayload.getRoleId() == UserRoleEnum.Employee.getRoleId()) {
                releaseUserDepartment(user);
                user.setRole(UserRoleEnum.Employee.getRoleId());
            }
        }
        userDao.save(user);
    }

    public void changeDepartment(ChangeDepartmentPayload changeDepartmentPayload){
        // 获取用户
        var userQuery = userDao.findById(changeDepartmentPayload.getUserId());
        if (userQuery.isEmpty()) {
            throw AdminControllerException.userNotExistException;
        }
        User user = userQuery.get();

        var departmentQuery = departmentDao.findById(changeDepartmentPayload.getDepartmentId());
        if (departmentQuery.isEmpty()) {
            throw AdminControllerException.departmentNotExistsException;
        }
        releaseUserDepartment(user);
        user.setDepartmentId(changeDepartmentPayload.getDepartmentId());
        userDao.save(user);
    }

    private void releaseUserDepartment(User user) {
        if (!(user.getRole() == UserRoleEnum.DepartmentManager.getRoleId() || 
            user.getRole() == UserRoleEnum.Manager.getRoleId())) {
            return ;
        }
        var departmentQuery = departmentDao.findById(user.getDepartmentId());
        if (departmentQuery.isEmpty()) {
            return ;
        }
        Department department = departmentQuery.get();

        department.setManagerId(null);
        departmentDao.save(department);
        user.setRole(UserRoleEnum.Employee.getRoleId());
        userDao.save(user);
    }
}