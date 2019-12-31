package com.example.travelmanager.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private String url;

    @Column(nullable = false)
    @NotNull
    private Date uploadTime;


    /***** 方法 *****/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setUploadTime() {
        // 这样只有日期
        //this.uploadTime = new Date(Calendar.getInstance().getTimeInMillis());
        this.uploadTime = new java.util.Date();
    }
}
