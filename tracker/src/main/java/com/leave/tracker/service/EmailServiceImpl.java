package com.leave.tracker.service;

import com.leave.tracker.entity.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendMail(EmailDetails details) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setFrom(sender);
            helper.setTo(details.getRecipient());
            helper.setSubject(details.getSubject());
            helper.setText(details.getMsgBody(), false);

            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while Sending Mail: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unexpected Error while Sending Mail: " + e.getMessage();
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(sender);
            helper.setTo(details.getRecipient());
            helper.setSubject(details.getSubject());
            helper.setText(details.getMsgBody(), false);

            if (details.getAttachment() != null) {
                FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
                if (file.exists()) {
                    helper.addAttachment(file.getFilename(), file);
                } else {
                    return "Attachment not found: " + details.getAttachment();
                }
            }

            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully with attachment!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail with attachment: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unexpected error: " + e.getMessage();
        }
    }
}
