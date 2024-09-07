package com.booksn.profile.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.booksn.profile.dto.ApiResponse;
import com.booksn.profile.dto.response.UserProfileResponse;
import com.booksn.profile.service.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/users/{profileId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String profileId) {
        log.debug("getProfile method start");
        UserProfileResponse userProfileResponse = userProfileService.getProfile(profileId);
        log.debug("getProfile method end");
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileResponse)
                .build();
    }

    @GetMapping("/users")
    ApiResponse<List<UserProfileResponse>> getAllProfiles() {
        log.debug("getAllProfiles method start");
        List<UserProfileResponse> userProfileResponses = userProfileService.getAllProfiles();
        log.debug("getAllProfiles method end");
        return ApiResponse.<List<UserProfileResponse>>builder()
                .result(userProfileResponses)
                .build();
    }

    @GetMapping("/users/my-profile")
    ApiResponse<UserProfileResponse> getMyProfile() {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getMyProfile())
                .build();
    }
}
