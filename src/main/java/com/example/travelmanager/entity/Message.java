package com.example.travelmanager.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "message")
public class Message {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter
    private String message;

    @Getter @Setter
    private Integer receiverId;

    // type = 0 is TravelApplication
    // type = 1 is PaymentApplication
    @Getter @Setter
    private Integer type;

    // 相应的资源ID
    @Getter @Setter
    private Integer resourceId;

    public static String messageGenerator(String checker, String action, String applyType) {
        return String.format("%s%s了你的%s申请", checker, action, applyType);
    }

}
