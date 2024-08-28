package com.booksn.gateway.service;

import com.booksn.gateway.dto.ApiResponse;
import com.booksn.gateway.dto.request.IntrospectRequest;
import com.booksn.gateway.dto.response.IntrospectResponse;
import com.booksn.gateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {

    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        // Call identity service to introspect token
        return identityClient.introspect(IntrospectRequest.builder().token(token).build());
    }
}
