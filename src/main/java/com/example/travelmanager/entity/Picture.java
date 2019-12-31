package com.example.travelmanager.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Picture {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    private String url;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    private Date uploadTime;

    public void setUploadTime() {
        // 这样只有日期
        //this.uploadTime = new Date(Calendar.getInstance().getTimeInMillis());
        this.uploadTime = new java.util.Date();
    }
}
