package com.booksn.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.booksn.identity.dto.request.RoleRequest;
import com.booksn.identity.dto.response.RoleResponse;
import com.booksn.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
