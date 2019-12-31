package com.example.travelmanager.entity;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class PaymentApplication {
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
    private Integer travelId;

    // "https://www.baidu.com https://www.google.com"
    private String invoiceURLs;

    private float hotelPayment;

    private float vehiclePayment;

    private float foodPayment;

    private float otherPayment;


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

    public Integer getTravelId() {
        return travelId;
    }

    public void setTravelId(Integer travelId) {
        this.travelId = travelId;
    }

    public String getInvoiceURLs() {
        return invoiceURLs;
    }

    public void setInvoiceURLs(String invoiceURLs) {
        this.invoiceURLs = invoiceURLs;
    }

    public float getHotelPayment() {
        return hotelPayment;
    }

    public void setHotelPayment(float hotelPayment) {
        this.hotelPayment = hotelPayment;
    }

    public float getVehiclePayment() {
        return vehiclePayment;
    }

    public void setVehiclePayment(float vehiclePayment) {
        this.vehiclePayment = vehiclePayment;
    }

    public float getFoodPayment() {
        return foodPayment;
    }

    public void setFoodPayment(float foodPayment) {
        this.foodPayment = foodPayment;
    }

    public float getOtherPayment() {
        return otherPayment;
    }

    public void setOtherPayment(float otherPayment) {
        this.otherPayment = otherPayment;
    }
}
