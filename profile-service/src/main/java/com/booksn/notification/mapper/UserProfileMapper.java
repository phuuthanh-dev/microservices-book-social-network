package com.booksn.notification.mapper;

import com.booksn.notification.dto.request.ProfileCreationRequest;
import com.booksn.notification.dto.response.UserProfileResponse;
import com.booksn.notification.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
