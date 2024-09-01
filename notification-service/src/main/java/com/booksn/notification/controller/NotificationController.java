package com.booksn.notification.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.booksn.event.dto.NotificationEvent;
import com.booksn.notification.dto.request.Recipient;
import com.booksn.notification.dto.request.SendEmailRequest;
import com.booksn.notification.service.EmailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    EmailService emailService;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent message) {
        emailService.sendEmail(SendEmailRequest.builder()
                .to(Recipient.builder().email(message.getRecipient()).build())
                .subject(message.getSubject())
                .htmlContent(message.getBody())
                .build());
    }
}
