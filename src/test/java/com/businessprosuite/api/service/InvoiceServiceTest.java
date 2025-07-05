package com.businessprosuite.api.service;

import com.businessprosuite.api.dto.finance.InvoiceDTO;
import com.businessprosuite.api.impl.finance.InvoiceServiceImpl;
import com.businessprosuite.api.model.finance.Invoice;
import com.businessprosuite.api.repository.finance.InvoiceRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.repository.customer.CustomerRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.mapper.InvoiceMapper;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.security.SecurityUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {
    @Mock
    private InvoiceRepository repo;

    @Mock
    private ConfigCompanyRepository companyRepo;

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private SecurityUserRepository userRepo;

    @Mock
    private InvoiceMapper invoiceMapper;

    @InjectMocks
    private InvoiceServiceImpl service;

    @Test
    void createInvoice_success() {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setDate(LocalDateTime.now());
        dto.setTotal(BigDecimal.valueOf(100.0));
        dto.setTax(BigDecimal.valueOf(10.0));
        dto.setDiscount(BigDecimal.valueOf(5.0));
        dto.setConfigCompanyId(1);
        dto.setCustomerId(2);
        dto.setSecurityUserId(3);

        when(companyRepo.findById(1)).thenReturn(java.util.Optional.of(new ConfigCompany()));
        when(customerRepo.findById(2)).thenReturn(java.util.Optional.of(new Customer()));
        when(userRepo.findById(3)).thenReturn(java.util.Optional.of(new SecurityUser()));

        Invoice toSave = new Invoice();
        when(invoiceMapper.toEntity(any(InvoiceDTO.class))).thenReturn(toSave);

        Invoice saved = new Invoice();
        saved.setId(1);
        saved.setFinInvDate(dto.getDate());
        saved.setFinInvTotal(BigDecimal.valueOf(100.0));
        saved.setFinInvTax(BigDecimal.valueOf(10.0));
        saved.setFinInvDiscount(BigDecimal.valueOf(5.0));

        InvoiceDTO savedDto = new InvoiceDTO();
        savedDto.setId(1);

        when(invoiceMapper.toDto(any(Invoice.class))).thenReturn(savedDto);

        when(repo.save(any(Invoice.class))).thenReturn(saved);

        InvoiceDTO result = service.create(dto);

        assertEquals(1, result.getId());
        verify(repo).save(any(Invoice.class));
    }
}

