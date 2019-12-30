package com.example.travelmanager.entity;

import com.example.travelmanager.service.CommonHelper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable = false)
    @NotNull
    // https://stackoverflow.com/questions/7439504/confusion-notnull-vs-columnnullable-false
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String passwordHash;

    // 可不为空
    private Integer departmentId;

    @Column(nullable = false)
    @NotNull
    private String workId;

    private String telephone;

    private String email;

    @Column(nullable = false)
    @NotNull
    private Boolean status;

    private Integer role;

    private String avatar;


    /****** Methods ******/
    public boolean validPassword(String password) {
        System.out.println(this.passwordHash);
        System.out.println(CommonHelper.MD5Encode(password));
        return this.passwordHash.equals(CommonHelper.MD5Encode(password));
    }

    public void setPassword(String password){
        this.passwordHash = CommonHelper.MD5Encode(password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) { }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
