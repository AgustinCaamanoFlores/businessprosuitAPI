package com.businessprosuite.api.service;

import com.businessprosuite.api.dto.finance.InvoiceDTO;
import com.businessprosuite.api.impl.finance.InvoiceServiceImpl;
import com.businessprosuite.api.model.finance.Invoice;
import com.businessprosuite.api.repository.finance.InvoiceRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.repository.customer.CustomerRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.mapper.InvoiceMapper;
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

        Invoice entity = new Invoice();
        when(invoiceMapper.toEntity(dto)).thenReturn(entity);

        when(companyRepo.findById(any())).thenReturn(java.util.Optional.of(new com.businessprosuite.api.model.config.ConfigCompany()));
        when(customerRepo.findById(any())).thenReturn(java.util.Optional.of(new com.businessprosuite.api.model.customer.Customer()));
        when(userRepo.findById(any())).thenReturn(java.util.Optional.of(new com.businessprosuite.api.model.security.SecurityUser()));

        Invoice saved = new Invoice();
        saved.setId(1);
        saved.setFinInvDate(dto.getDate());
        saved.setFinInvTotal(BigDecimal.valueOf(100.0));
        saved.setFinInvTax(BigDecimal.valueOf(10.0));
        saved.setFinInvDiscount(BigDecimal.valueOf(5.0));

        when(repo.save(any(Invoice.class))).thenReturn(saved);
        when(invoiceMapper.toDto(saved)).thenReturn(InvoiceDTO.builder()
                .id(1)
                .date(dto.getDate())
                .configCompanyId(dto.getConfigCompanyId())
                .customerId(dto.getCustomerId())
                .securityUserId(dto.getSecurityUserId())
                .total(dto.getTotal())
                .tax(dto.getTax())
                .discount(dto.getDiscount())
                .net(saved.getFinInvNet())
                .createdAt(saved.getFinInvCreatedAt())
                .updatedAt(saved.getFinInvUpdatedAt())
                .build());

        InvoiceDTO result = service.create(dto);

        assertEquals(1, result.getId());
        verify(repo).save(any(Invoice.class));
    }
}

