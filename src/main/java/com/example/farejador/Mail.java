package com.example.farejador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.farejador.models.Ape;

@Component
public class Mail {

    public final JavaMailSender emailSender;

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

    public static String mountBodyMail(List<Ape> apes) {
        return apes.stream()
                .map(Ape::getLink)
                .collect(Collectors.joining("\n\n"));
    }
}
