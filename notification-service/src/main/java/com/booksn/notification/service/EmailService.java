package com.booksn.notification.service;

import com.booksn.notification.dto.request.EmailRequest;
import com.booksn.notification.dto.response.EmailResponse;
import com.booksn.notification.exception.AppException;
import com.booksn.notification.exception.ErrorCode;
import com.booksn.notification.dto.request.SendEmailRequest;
import com.booksn.notification.dto.request.Sender;
import com.booksn.notification.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    String apiKey = "";

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Book Social Network")
                        .email("phuuthanh2003@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e){
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
