package com.example.farejador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Mail {

    public final JavaMailSender emailSender;

    public static String SUBJECT = "NOVIDADE DO FAREJADOR!";
    public static String TO = "henrysjfarias@gmail.com";

    @Autowired
    public Mail(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public static String mountBodyMail(String links) {
        return links;
    }
}
