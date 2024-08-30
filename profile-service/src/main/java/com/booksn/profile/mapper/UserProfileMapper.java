package com.booksn.profile.mapper;

import com.booksn.profile.dto.request.ProfileCreationRequest;
import com.booksn.profile.dto.response.UserProfileResponse;
import com.booksn.profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
