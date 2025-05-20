package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.JournalDTO;
import java.util.List;

public interface JournalService {
    List<JournalDTO> findAll();
    JournalDTO findById(Integer id);
    JournalDTO create(JournalDTO dto);
    JournalDTO update(Integer id, JournalDTO dto);
    void delete(Integer id);
}