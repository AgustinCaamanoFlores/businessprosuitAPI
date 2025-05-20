package com.businessprosuite.api.service.document;

import com.businessprosuite.api.dto.document.DocumentDTO;
import java.util.List;

public interface DocumentService {
    List<DocumentDTO> findAll();
    DocumentDTO findById(Integer id);
    DocumentDTO create(DocumentDTO dto);
    DocumentDTO update(Integer id, DocumentDTO dto);
    void delete(Integer id);
}
