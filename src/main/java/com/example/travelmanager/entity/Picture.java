package com.example.travelmanager.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "picture")
public class Picture {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
