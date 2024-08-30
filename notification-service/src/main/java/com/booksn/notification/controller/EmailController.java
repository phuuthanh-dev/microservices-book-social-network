package com.booksn.notification.controller;

import com.booksn.notification.dto.ApiResponse;
import com.booksn.notification.dto.response.EmailResponse;
import com.booksn.notification.service.EmailService;
import com.booksn.notification.dto.request.SendEmailRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    ApiResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest request){
        return ApiResponse.<EmailResponse>builder()
                .result(emailService.sendEmail(request))
                .message("Email sent successfully")
                .build();
    }
}