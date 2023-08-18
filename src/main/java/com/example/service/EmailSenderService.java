package com.example.service;

import com.example.entity.ProfileEntity;
import com.example.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailHistoryService emailHistoryService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${server.url}")
    private String serverUrl;

    public void senderEmail(ProfileEntity entity) {
        String jwt = jwtUtil.encode(entity.getId(), entity.getEmail());
        String url = serverUrl + "/api/v1/auth/verification/email/" + jwt;
        String subject = "You tube uz Verification link";
        String builder = String.format("<h1>Hello %s</h1>", entity.getName()) +
                " <p>" +
                String.format("<a href=\"%s\">Click the link to verify your account! </a>", url) +
                " </p>";

        sendMimeEmail(entity.getEmail(), subject, builder);
        emailHistoryService.sendEmailSave(builder, entity.getEmail(), subject);
    }

    private void sendMimeEmail(String toAccount, String subject, String text) {
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MimeMessage msg = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(msg, true);
                    helper.setTo(toAccount);
                    helper.setSubject(subject);
                    helper.setText("verification", text);
                    javaMailSender.send(msg);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        emailExecutor.shutdown();
    }

    public void updateEmailSender(ProfileEntity entity,String email) {
        String jwt = jwtUtil.encode(entity.getId(), entity.getEmail());
        String url = serverUrl + "/api/v1/profile/update-email/verification/" + jwt;
        String subject = "You tube uz profile update email Verification link";
        String builder = String.format("<h1>Hello %s</h1>", entity.getName()) +
                " <p>" +
                String.format("<a href=\"%s\">Click the link to verify your account! </a>", url) +
                " </p>";
        sendMimeEmail(email, subject, builder);
        emailHistoryService.sendEmailSave(builder, email, subject);
    }
}

