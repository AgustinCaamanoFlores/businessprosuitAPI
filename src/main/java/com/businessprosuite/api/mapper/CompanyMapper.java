package com.businessprosuite.api.mapper;

import com.businessprosuite.api.dto.company.CompanyDTO;
import com.businessprosuite.api.model.company.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "configCompanyId", source = "configCompany.id")
    @Mapping(target = "name", source = "cmpName")
    @Mapping(target = "address", source = "cmpAddress")
    @Mapping(target = "phone", source = "cmpPhone")
    @Mapping(target = "email", source = "cmpEmail")
    @Mapping(target = "taxId", source = "cmpTaxId")
    @Mapping(target = "countryCode", source = "cmpConfigCountryCodigo.codigo")
    @Mapping(target = "createdAt", source = "cmpCreatedAt")
    @Mapping(target = "updatedAt", source = "cmpUpdatedAt")
    CompanyDTO toDto(Company entity);

    @Mapping(target = "configCompany", ignore = true)
    @Mapping(target = "cmpConfigCountryCodigo", ignore = true)
    @Mapping(target = "cmpName", source = "name")
    @Mapping(target = "cmpAddress", source = "address")
    @Mapping(target = "cmpPhone", source = "phone")
    @Mapping(target = "cmpEmail", source = "email")
    @Mapping(target = "cmpTaxId", source = "taxId")
    @Mapping(target = "cmpCreatedAt", source = "createdAt")
    @Mapping(target = "cmpUpdatedAt", source = "updatedAt")
    Company toEntity(CompanyDTO dto);

    @Mapping(target = "configCompany", ignore = true)
    @Mapping(target = "cmpConfigCountryCodigo", ignore = true)
    @Mapping(target = "cmpName", source = "name")
    @Mapping(target = "cmpAddress", source = "address")
    @Mapping(target = "cmpPhone", source = "phone")
    @Mapping(target = "cmpEmail", source = "email")
    @Mapping(target = "cmpTaxId", source = "taxId")
    @Mapping(target = "cmpUpdatedAt", source = "updatedAt")
    void updateEntity(CompanyDTO dto, @MappingTarget Company entity);
}
