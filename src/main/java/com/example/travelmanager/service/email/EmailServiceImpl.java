package com.example.travelmanager.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Async("taskExecutor")
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        try{
            message.setFrom(from); // 邮件发送者
            message.setTo(to.split(";")); // 邮件接受者
            message.setSubject(subject); // 主题
            message.setText(content); // 内容
            mailSender.send(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}