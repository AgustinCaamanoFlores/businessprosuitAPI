package com.businessprosuite.api.mapper;

import com.businessprosuite.api.dto.company.CompanyBranchDTO;
import com.businessprosuite.api.model.company.CompanyBranch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompanyBranchMapper {

    @Mapping(target = "companyId", source = "brcCmp.id")
    @Mapping(target = "name", source = "brcName")
    @Mapping(target = "address", source = "brcAddress")
    @Mapping(target = "phone", source = "brcPhone")
    @Mapping(target = "createdAt", source = "brcCreatedAt")
    @Mapping(target = "updatedAt", source = "brcUpdatedAt")
    CompanyBranchDTO toDto(CompanyBranch entity);

    @Mapping(target = "brcCmp", ignore = true)
    @Mapping(target = "brcName", source = "name")
    @Mapping(target = "brcAddress", source = "address")
    @Mapping(target = "brcPhone", source = "phone")
    @Mapping(target = "brcCreatedAt", source = "createdAt")
    @Mapping(target = "brcUpdatedAt", source = "updatedAt")
    CompanyBranch toEntity(CompanyBranchDTO dto);

    @Mapping(target = "brcCmp", ignore = true)
    @Mapping(target = "brcName", source = "name")
    @Mapping(target = "brcAddress", source = "address")
    @Mapping(target = "brcPhone", source = "phone")
    @Mapping(target = "brcUpdatedAt", source = "updatedAt")
    void updateEntity(CompanyBranchDTO dto, @MappingTarget CompanyBranch entity);
}
