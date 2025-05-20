package com.businessprosuite.api.service.document;

import com.businessprosuite.api.dto.document.DocumentVersionDTO;
import java.util.List;

public interface DocumentVersionService {
    List<DocumentVersionDTO> findAll();
    DocumentVersionDTO findById(Integer id);
    DocumentVersionDTO create(DocumentVersionDTO dto);
    DocumentVersionDTO update(Integer id, DocumentVersionDTO dto);
    void delete(Integer id);
}