package com.booksn.identity.mapper;

import com.booksn.identity.dto.request.ProfileCreationRequest;
import com.booksn.identity.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
