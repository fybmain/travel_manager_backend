package com.example.travelmanager.entity;

import com.example.travelmanager.service.CommonHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    // https://stackoverflow.com/questions/7439504/confusion-notnull-vs-columnnullable-false
    private Integer id;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    private String name;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    @JsonIgnore
    private String passwordHash;

    // 可不为空
    @Getter @Setter
    private Integer departmentId;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    private String workId;

    @Getter @Setter
    private String telephone;

    @Getter @Setter
    private String email;

    @Getter @Setter
    @Column(nullable = false)
    @NotNull
    private Boolean status;

    @Getter @Setter
    private Integer role;

    @Getter @Setter
    @JsonIgnore
    private String avatar;

    /****** Methods ******/
    public boolean validPassword(String password) {
        return this.passwordHash.equals(CommonHelper.MD5Encode(password));
    }

    public void setPassword(String password){
        this.passwordHash = CommonHelper.MD5Encode(password);
    }
}
