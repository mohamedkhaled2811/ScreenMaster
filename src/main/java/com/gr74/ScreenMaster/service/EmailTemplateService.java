package com.gr74.ScreenMaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailTemplateService {

    @Autowired
    private TemplateEngine templateEngine;

    public String generateVerificationEmail( String userEmail,
                                            String verificationCode, String verificationUrl) {
        Context context = new Context();
//        context.setVariable("userName", userName);
        context.setVariable("userEmail", userEmail);
        context.setVariable("verificationCode", verificationCode);
        context.setVariable("verificationUrl", verificationUrl);
        context.setVariable("appName", "Your App Name");
        context.setVariable("supportEmail", "support@yourapp.com");
        context.setVariable("companyAddress", "123 Your Street, City, Country");
        context.setVariable("unsubscribeUrl", "https://yourapp.com/unsubscribe");

        return templateEngine.process("email/verification", context);
    }
}