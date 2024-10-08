package com.booksn.gateway.configuration;

import com.booksn.gateway.dto.ApiResponse;
import com.booksn.gateway.service.IdentityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    IdentityService identityService;
    ObjectMapper objectMapper;

    @NonFinal
    private String[] publicEndpoints = {"/identity/auth/.*", "/identity/users/registration", "/notification/email/send"};

    @Value("${app.api-prefix}")
    @NonFinal
    private String API_PREFIX;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Skip public endpoints
        if (isPublicEndpoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        // Get token from request
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader)) {
            return unauthenticated(exchange.getResponse());
        }

        String authHeaderValue = authHeader.get(0);
        if (!authHeaderValue.startsWith("Bearer ")) {
            return unauthenticated(exchange.getResponse());
        }
        String token = authHeaderValue.substring(7);

        // Verify token
        // Delegate to identity service
        return identityService.introspect(token).flatMap(introspectResponse -> {
            if (!introspectResponse.getResult().isValid()) {
                return unauthenticated(exchange.getResponse());
            }
            return chain.filter(exchange);

        }).onErrorResume(throwable -> {
            if (throwable instanceof ResponseStatusException responseStatusException &&
                    responseStatusException.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT) {
                return timeoutResponse(exchange.getResponse());
            } else {
                return unauthenticated(exchange.getResponse());
            }
        });
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        for (String publicEndpoint : publicEndpoints) {
            if (request.getURI().getPath().matches(API_PREFIX + publicEndpoint)) {
                return true;
            }
        }
        return false;
    }

    Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder().code(HttpStatus.UNAUTHORIZED.value()).message("Unauthenticated").build();
        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    Mono<Void> timeoutResponse(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder().code(HttpStatus.REQUEST_TIMEOUT.value()).message("Request timed out. Please try again.").build();

        String body;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.REQUEST_TIMEOUT);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
