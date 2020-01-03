package com.example.travelmanager.service.email;

public interface EmailService {
    void sendSimpleMail(String to, String subject, String content);
}