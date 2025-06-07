package com.chimera.financialtracker.security.auth.listener;

import com.chimera.financialtracker.security.auth.event.OnRegistrationCompleteEvent;
import com.chimera.financialtracker.security.auth.model.Users;
import com.chimera.financialtracker.security.auth.service.AuthService;
import com.chimera.financialtracker.security.auth.service.FTUserDetailsService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final AuthService authService;

    private final MessageSource messages;

    private final JavaMailSender mailSender;

    public RegistrationListener(
            AuthService authService,
            MessageSource messages,
            JavaMailSender mailSender
    ){
        this.authService = authService;
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event){
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event){
        Users user = event.getUser();
        String token = UUID.randomUUID().toString();
        authService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = messages.getMessage("message.regSucc", null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("registration@chimeratech.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + confirmationUrl);
        mailSender.send(email);
    }
}
