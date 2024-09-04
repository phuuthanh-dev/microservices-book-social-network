package com.booksn.identity.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.booksn.identity.configuration.AuthenticationRequestInterceptor;
import com.booksn.identity.dto.ApiResponse;
import com.booksn.identity.dto.request.ProfileCreationRequest;
import com.booksn.identity.dto.response.UserProfileResponse;

@FeignClient(
        name = "profile-service",
        configuration = {AuthenticationRequestInterceptor.class})
public interface ProfileClient {
    @PostMapping(value = "/profile/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);
}
