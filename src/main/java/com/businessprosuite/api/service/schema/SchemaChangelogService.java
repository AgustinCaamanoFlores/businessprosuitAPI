package com.businessprosuite.api.service.schema;

import com.businessprosuite.api.dto.schema.SchemaChangelogDTO;
import java.util.List;

public interface SchemaChangelogService {
    List<SchemaChangelogDTO> findAll();
    SchemaChangelogDTO findById(Integer id);
    SchemaChangelogDTO create(SchemaChangelogDTO dto);
    SchemaChangelogDTO update(Integer id, SchemaChangelogDTO dto);
    void delete(Integer id);
}