package com.booksn.identity.mapper;

import org.mapstruct.Mapper;

import com.booksn.identity.dto.request.PermissionRequest;
import com.booksn.identity.dto.response.PermissionResponse;
import com.booksn.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
