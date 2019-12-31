package com.example.travelmanager.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Department {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    private String name;

    // manager可以为空
    @Getter @Setter
    private Integer managerId;

}
