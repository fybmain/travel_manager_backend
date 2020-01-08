package com.example.travelmanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "payment_application")
public class PaymentApplication {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    private Integer applicantId;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    private Integer departmentId;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date applyTime;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    private Integer status;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    private Integer travelId;

    // "https://www.baidu.com https://www.google.com"
    @Getter @Setter
    private String invoiceURLs;

    @Getter @Setter
    private float hotelPayment;

    @Getter @Setter
    private float vehiclePayment;

    @Getter @Setter
    private float foodPayment;

    @Getter @Setter
    private float otherPayment;

}
