package com.leave.tracker.service;

import com.leave.tracker.entity.EmailDetails;

public interface EmailService {
    String sendMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
}
