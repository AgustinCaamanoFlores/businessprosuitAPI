package com.businessprosuite.api.service;

import com.businessprosuite.api.dto.finance.InvoiceDTO;
import com.businessprosuite.api.impl.finance.InvoiceServiceImpl;
import com.businessprosuite.api.model.finance.Invoice;
import com.businessprosuite.api.repository.finance.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
@Disabled("Not implemented")
@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {
    @Mock
    private InvoiceRepository repo;

    @InjectMocks
    private InvoiceServiceImpl service;

    @Test
    void createInvoice_success() {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setDate(LocalDateTime.now());
        dto.setTotal(BigDecimal.valueOf(100));
        dto.setTax(BigDecimal.valueOf(10));
        dto.setDiscount(BigDecimal.valueOf(5));

        Invoice saved = new Invoice();
        saved.setId(1);
        saved.setFinInvDate(dto.getDate());
        saved.setFinInvTotal(BigDecimal.valueOf(100));
        saved.setFinInvTax(BigDecimal.valueOf(10));
        saved.setFinInvDiscount(BigDecimal.valueOf(5));

        when(repo.save(any(Invoice.class))).thenReturn(saved);

        InvoiceDTO result = service.create(dto);

        assertEquals(1, result.getId());
        verify(repo).save(any(Invoice.class));
    }
}
