package com.gr74.ScreenMaster.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailTemplateService templateService;


    public void sendVerificationEmail(String to,
                                      String verificationCode, String verificationUrl)
            throws MessagingException {

        String htmlContent = templateService.generateVerificationEmail(
                to, verificationCode, verificationUrl
        );


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("screenmaster.app@example.com");
        helper.setTo(to);
        helper.setSubject("Verify Your Email Address - " + verificationCode);
        helper.setText(htmlContent, true); // true = HTML content

        mailSender.send(message);
    }

}