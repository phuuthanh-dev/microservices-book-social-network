package com.booksn.identity.mapper;

import org.mapstruct.Mapper;

import com.booksn.identity.dto.request.ProfileCreationRequest;
import com.booksn.identity.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
