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
@Table(name = "travel_application")
public class TravelApplication {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy=GenerationType.AUTO)
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
    private Boolean paid;

    // 2019-01-01
    @Getter @Setter
    @Column(nullable = false) @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date startTime;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date endTime;

    @Getter @Setter
    private String province;

    @Getter
    @Setter
    private String city;

    @Getter @Setter
    private String reason;

    @Getter @Setter
    private float hotelBudget;

    @Getter @Setter
    private float vehicleBudget;

    @Getter @Setter
    private float foodBudget;

    @Getter @Setter
    private float otherBudget;
}
