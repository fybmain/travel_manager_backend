package com.example.travelmanager.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Table(name = "department")
public class Department {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter
    @Column(nullable = false) @NotNull
    private String name;

    // manager可以为空
    @Getter @Setter
    private Integer managerId;

}
