package com.businessprosuite.api.mapper;

import com.businessprosuite.api.dto.security.SecurityUserDTO;
import com.businessprosuite.api.model.security.SecurityUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SecurityUserMapper {

    @Mapping(target = "name", source = "secusName")
    @Mapping(target = "password", source = "secusPassword")
    @Mapping(target = "email", source = "secusEmail")
    @Mapping(target = "available", source = "secusAvailable")
    @Mapping(target = "lastLogin", source = "secusLastLogin")
    @Mapping(target = "active", source = "secusActive")
    @Mapping(target = "mfaEnabled", source = "secusMfaEnabled")
    @Mapping(target = "mfaSecret", source = "secusMfaSecret")
    @Mapping(target = "roleId", source = "secusRole.id")
    @Mapping(target = "companyId", source = "secusCmp.id")
    @Mapping(target = "lastPasswordChange", source = "secusLastPasswordChange")
    @Mapping(target = "failedAttempts", source = "secusFailedAttempts")
    @Mapping(target = "residence", source = "secusResidence")
    @Mapping(target = "createdAt", source = "secusCreatedAt")
    @Mapping(target = "updatedAt", source = "secusUpdatedAt")
    SecurityUserDTO toDto(SecurityUser entity);

    @Mapping(target = "secusRole", ignore = true)
    @Mapping(target = "secusCmp", ignore = true)
    @Mapping(target = "secusName", source = "name")
    @Mapping(target = "secusPassword", source = "password")
    @Mapping(target = "secusEmail", source = "email")
    @Mapping(target = "secusAvailable", source = "available")
    @Mapping(target = "secusLastLogin", source = "lastLogin")
    @Mapping(target = "secusActive", source = "active")
    @Mapping(target = "secusMfaEnabled", source = "mfaEnabled")
    @Mapping(target = "secusMfaSecret", source = "mfaSecret")
    @Mapping(target = "secusLastPasswordChange", source = "lastPasswordChange")
    @Mapping(target = "secusFailedAttempts", source = "failedAttempts")
    @Mapping(target = "secusResidence", source = "residence")
    @Mapping(target = "secusCreatedAt", source = "createdAt")
    @Mapping(target = "secusUpdatedAt", source = "updatedAt")
    SecurityUser toEntity(SecurityUserDTO dto);

    @Mapping(target = "secusRole", ignore = true)
    @Mapping(target = "secusCmp", ignore = true)
    @Mapping(target = "secusName", source = "name")
    @Mapping(target = "secusPassword", source = "password")
    @Mapping(target = "secusEmail", source = "email")
    @Mapping(target = "secusAvailable", source = "available")
    @Mapping(target = "secusLastLogin", source = "lastLogin")
    @Mapping(target = "secusActive", source = "active")
    @Mapping(target = "secusMfaEnabled", source = "mfaEnabled")
    @Mapping(target = "secusMfaSecret", source = "mfaSecret")
    @Mapping(target = "secusLastPasswordChange", source = "lastPasswordChange")
    @Mapping(target = "secusFailedAttempts", source = "failedAttempts")
    @Mapping(target = "secusResidence", source = "residence")
    @Mapping(target = "secusUpdatedAt", source = "updatedAt")
    void updateEntity(SecurityUserDTO dto, @MappingTarget SecurityUser entity);
}
