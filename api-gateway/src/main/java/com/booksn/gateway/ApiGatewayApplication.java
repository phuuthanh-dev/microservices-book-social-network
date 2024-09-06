package com.booksn.gateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class ApiGatewayApplication {

    private static final String X_RESPONSE_TIME_HEADER = "X-Response-Time";

    @Value("${app.api-prefix}")
    private String API_PREFIX;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("identity-service", r -> r
                        .path(API_PREFIX + "/identity/**")
                        .filters(f -> f
                                .addResponseHeader(X_RESPONSE_TIME_HEADER , LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("identityCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport"))
                                .stripPrefix(1))
                        .uri("lb://IDENTITY-SERVICE"))
                .route("profile-service", r -> r
                        .path(API_PREFIX + "/profile/users/**")
                        .filters(f -> f
                                .addResponseHeader(X_RESPONSE_TIME_HEADER , LocalDateTime.now().toString())
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                                .stripPrefix(1))
                        .uri("lb://PROFILE-SERVICE"))
                .route("notification-service", r -> r
                        .path(API_PREFIX + "/notification/**")
                        .filters(f -> f
                                .addResponseHeader(X_RESPONSE_TIME_HEADER , LocalDateTime.now().toString())
                                .stripPrefix(1))
                        .uri("lb://NOTIFICATION-SERVICE"))
                .route("post-service", r -> r
                        .path(API_PREFIX + "/post/**")
                        .filters(f -> f
                                .addResponseHeader(X_RESPONSE_TIME_HEADER , LocalDateTime.now().toString())
                                .stripPrefix(1))
                        .uri("lb://POST-SERVICE"))
                .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
    }
}
