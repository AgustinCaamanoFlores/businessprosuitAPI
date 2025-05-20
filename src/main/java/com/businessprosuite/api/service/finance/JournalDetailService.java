package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.JournalDetailDTO;

import java.util.List;

public interface JournalDetailService {
    List<JournalDetailDTO> findAll();
    JournalDetailDTO findById(Integer id);
    JournalDetailDTO create(JournalDetailDTO dto);
    JournalDetailDTO update(Integer id, JournalDetailDTO dto);
    void delete(Integer id);
}