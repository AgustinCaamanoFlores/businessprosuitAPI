package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.InvoiceDetailDTO;

import java.util.List;

public interface InvoiceDetailService {
    List<InvoiceDetailDTO> findAll();
    InvoiceDetailDTO findById(Integer id);
    InvoiceDetailDTO create(InvoiceDetailDTO dto);
    InvoiceDetailDTO update(Integer id, InvoiceDetailDTO dto);
    void delete(Integer id);
}