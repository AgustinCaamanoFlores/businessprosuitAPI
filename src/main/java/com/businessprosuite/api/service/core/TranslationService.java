package com.businessprosuite.api.service.core;

import com.businessprosuite.api.dto.core.TranslationDTO;
import java.util.List;

public interface TranslationService {
    List<TranslationDTO> findAll();
    TranslationDTO findById(Integer id);
    TranslationDTO create(TranslationDTO dto);
    TranslationDTO update(Integer id, TranslationDTO dto);
    void delete(Integer id);
}
