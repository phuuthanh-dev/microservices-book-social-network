package com.booksn.post.repository.httpclient;

import com.booksn.post.dto.ApiResponse;
import com.booksn.post.dto.response.UserProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class ProfileFallback implements ProfileClient {
    @Override
    public ApiResponse<UserProfileResponse> getUserById(String userId) {
        return null;
    }
}
