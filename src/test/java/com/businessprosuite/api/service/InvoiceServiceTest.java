package com.businessprosuite.api.service;

import com.businessprosuite.api.dto.InvoiceDTO;
import com.businessprosuite.api.model.Invoice;
import com.businessprosuite.api.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {
    @Mock
    private InvoiceRepository repo;

    @InjectMocks
    private InvoiceService service;

    @Test
    void createInvoice_success() {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setInvoiceDate(LocalDateTime.now());
        dto.setTotal(100.0);
        dto.setTax(10.0);
        dto.setDiscount(5.0);

        Invoice saved = new Invoice();
        saved.setId(1);
        saved.setInvoiceDate(dto.getInvoiceDate());
        saved.setTotal(100.0);
        saved.setTax(10.0);
        saved.setDiscount(5.0);

        when(repo.save(any(Invoice.class))).thenReturn(saved);

        InvoiceDTO result = service.createInvoice(dto);

        assertEquals(1, result.getId());
        verify(repo).save(any(Invoice.class));
    }
}

