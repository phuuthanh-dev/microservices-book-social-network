package com.booksn.post.repository.httpclient;

import com.booksn.post.dto.ApiResponse;
import com.booksn.post.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", fallback = ProfileFallback.class)
public interface ProfileClient {
    @GetMapping("/profile/internal/users/{userId}")
    ApiResponse<UserProfileResponse> getUserById(@PathVariable String userId);
}
