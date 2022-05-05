package com.egersistemasavancados.sbootreddit.service;

import com.egersistemasavancados.sbootreddit.domain.NotificationEmail;
import com.egersistemasavancados.sbootreddit.service.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MailBuilderService mailBuilderService;

    @Async
    public void sendMail(NotificationEmail email) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        try {
            messageHelper.setFrom("sboot-reddit@email.com");
            messageHelper.setTo(email.getRecipient());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(mailBuilderService.build(email.getBody()));
            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new GlobalException(ex.getMessage());
        }
    }
}
