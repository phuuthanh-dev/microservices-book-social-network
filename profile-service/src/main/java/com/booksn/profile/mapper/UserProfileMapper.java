package com.booksn.profile.mapper;

import org.mapstruct.Mapper;

import com.booksn.profile.dto.request.ProfileCreationRequest;
import com.booksn.profile.dto.response.UserProfileResponse;
import com.booksn.profile.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
