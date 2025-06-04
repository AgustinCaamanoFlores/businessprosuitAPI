package com.businessprosuite.api.mapper;

import com.businessprosuite.api.dto.document.DocumentVersionDTO;
import com.businessprosuite.api.model.document.DocumentVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DocumentVersionMapper {

    @Mapping(target = "documentId", source = "doc.id")
    @Mapping(target = "versionNumber", source = "versionNumber")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "createdAt", source = "createdAt")
    DocumentVersionDTO toDto(DocumentVersion entity);

    @Mapping(target = "doc", ignore = true)
    @Mapping(target = "versionNumber", source = "versionNumber")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "createdAt", source = "createdAt")
    DocumentVersion toEntity(DocumentVersionDTO dto);

    @Mapping(target = "doc", ignore = true)
    @Mapping(target = "versionNumber", source = "versionNumber")
    @Mapping(target = "url", source = "url")
    void updateEntity(DocumentVersionDTO dto, @MappingTarget DocumentVersion entity);
}
