package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.FinanceConsReportDTO;

import java.util.List;

public interface FinanceConsReportService {
    List<FinanceConsReportDTO> findAll();
    FinanceConsReportDTO findById(Integer id);
    FinanceConsReportDTO create(FinanceConsReportDTO dto);
    FinanceConsReportDTO update(Integer id, FinanceConsReportDTO dto);
    void delete(Integer id);
}