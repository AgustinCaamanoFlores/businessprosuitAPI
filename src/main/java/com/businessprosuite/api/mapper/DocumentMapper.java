package com.businessprosuite.api.mapper;

import com.businessprosuite.api.dto.document.DocumentDTO;
import com.businessprosuite.api.model.document.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "companyId", source = "docCmp.id")
    @Mapping(target = "name", source = "docName")
    @Mapping(target = "type", source = "docType")
    @Mapping(target = "url", source = "docUrl")
    @Mapping(target = "createdAt", source = "docCreatedAt")
    DocumentDTO toDto(Document entity);

    @Mapping(target = "docCmp", ignore = true)
    @Mapping(target = "docName", source = "name")
    @Mapping(target = "docType", source = "type")
    @Mapping(target = "docUrl", source = "url")
    @Mapping(target = "docCreatedAt", source = "createdAt")
    Document toEntity(DocumentDTO dto);

    @Mapping(target = "docCmp", ignore = true)
    @Mapping(target = "docName", source = "name")
    @Mapping(target = "docType", source = "type")
    @Mapping(target = "docUrl", source = "url")
    void updateEntity(DocumentDTO dto, @MappingTarget Document entity);
}
