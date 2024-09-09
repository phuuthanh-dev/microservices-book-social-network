package com.booksn.identity.repository.httpclient;

import com.booksn.identity.dto.ApiResponse;
import com.booksn.identity.dto.request.ProfileCreationRequest;
import com.booksn.identity.dto.response.UserProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class ProfileFallback implements ProfileClient {
    @Override
    public ApiResponse<UserProfileResponse> createProfile(ProfileCreationRequest request) {
        return null;
    }

    @Override
    public ApiResponse<UserProfileResponse> getMyProfile() {
        return null;
    }
}
