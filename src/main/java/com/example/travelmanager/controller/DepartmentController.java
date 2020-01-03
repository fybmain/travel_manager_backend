package com.example.travelmanager.controller;

import com.example.travelmanager.config.Constant;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.dao.DepartmentDao;
import com.example.travelmanager.entity.Department;
import com.example.travelmanager.service.auth.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private AuthService authService;

    @Autowired
    private DepartmentDao departmentDao;

    @ApiOperation(value = "get all departments")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{code=0, msg='success'}" ,response = ResultBean.class)
    })
    @GetMapping("/all")
    public HttpEntity getAllDepartment(
            @RequestHeader(Constant.HEADER_STRING) String auth
    ) {
        authService.authorize(auth);

        List<Department> departments = new ArrayList<Department>();
        departmentDao.findAll().forEach((department -> {
            departments.add(department);
        }));
        return ResultBean.success(departments);
    }
}
