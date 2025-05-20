package com.businessprosuite.api.service.finance;

import com.businessprosuite.api.dto.finance.InvoiceDTO;
import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> findAll();
    InvoiceDTO findById(Integer id);
    InvoiceDTO create(InvoiceDTO dto);
    InvoiceDTO update(Integer id, InvoiceDTO dto);
    void delete(Integer id);
}