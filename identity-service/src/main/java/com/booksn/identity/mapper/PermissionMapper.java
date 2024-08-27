package com.booksn.identity.mapper;

import com.booksn.identity.entity.Permission;
import org.mapstruct.Mapper;

import com.booksn.identity.dto.request.PermissionRequest;
import com.booksn.identity.dto.response.PermissionResponse;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
