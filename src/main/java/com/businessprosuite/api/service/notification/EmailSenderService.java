package com.businessprosuite.api.service.notification;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String body);
}
