package com.example.travelmanager.entity;

import io.swagger.models.auth.In;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class TravelApplication {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private Integer applicantId;

    @Column(nullable = false)
    @NotNull
    private Integer departmentId;

    @Column(nullable = false)
    @NotNull
    private Date applyTime;

    @Column(nullable = false)
    @NotNull
    private Integer status;

    @Column(nullable = false)
    @NotNull
    private Boolean paid;

    // 2019-01-01
    @Column(nullable = false)
    @NotNull
    private String startTime;

    @Column(nullable = false)
    @NotNull
    private String endTime;

    private String province;

    private String city;

    private String reason;

    private float hotelBudget;

    private float vehicleBudget;

    private float foodBudget;

    private float otherBudget;


    /*********** 方法 ***********/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public float getHotelBudget() {
        return hotelBudget;
    }

    public void setHotelBudget(float hotelBudget) {
        this.hotelBudget = hotelBudget;
    }

    public float getVehicleBudget() {
        return vehicleBudget;
    }

    public void setVehicleBudget(float vehicleBudget) {
        this.vehicleBudget = vehicleBudget;
    }

    public float getFoodBudget() {
        return foodBudget;
    }

    public void setFoodBudget(float foodBudget) {
        this.foodBudget = foodBudget;
    }

    public float getOtherBudget() {
        return otherBudget;
    }

    public void setOtherBudget(float otherBudget) {
        this.otherBudget = otherBudget;
    }
}
