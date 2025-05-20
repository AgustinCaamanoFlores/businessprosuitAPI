package com.businessprosuite.api.service.schema;

import com.businessprosuite.api.dto.schema.SchemaVersionDTO;
import java.util.List;

public interface SchemaVersionService {
    List<SchemaVersionDTO> findAll();
    SchemaVersionDTO findById(Integer id);
    SchemaVersionDTO create(SchemaVersionDTO dto);
    SchemaVersionDTO update(Integer id, SchemaVersionDTO dto);
    void delete(Integer id);
}