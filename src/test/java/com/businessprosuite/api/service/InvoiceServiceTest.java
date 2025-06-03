package com.businessprosuite.api.service;

import com.businessprosuite.api.dto.finance.InvoiceDTO;
import com.businessprosuite.api.impl.finance.InvoiceServiceImpl;
import com.businessprosuite.api.model.finance.Invoice;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.finance.InvoiceRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.repository.customer.CustomerRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {
    
    @Mock
    private InvoiceRepository invoiceRepo;
    
    @Mock
    private ConfigCompanyRepository companyRepo;
    
    @Mock
    private CustomerRepository customerRepo;
    
    @Mock
    private SecurityUserRepository userRepo;

    @InjectMocks
    private InvoiceServiceImpl service;

    @Test
    void createInvoice_success() {
        // Arrange
        InvoiceDTO dto = InvoiceDTO.builder()
                .configCompanyId(1)
                .customerId(1)
                .securityUserId(1)
                .date(LocalDateTime.now())
                .total(BigDecimal.valueOf(100.0))
                .tax(BigDecimal.valueOf(10.0))
                .discount(BigDecimal.valueOf(5.0))
                .paymentStatus("PENDING")
                .build();

        ConfigCompany company = ConfigCompany.builder().id(1).build();
        Customer customer = Customer.builder().id(1).build();
        SecurityUser user = SecurityUser.builder().id(1).build();
        
        Invoice savedInvoice = Invoice.builder()
                .id(1)
                .configCompany(company)
                .finInvDate(dto.getDate())
                .finInvCus(customer)
                .finInvTotal(dto.getTotal())
                .finInvTax(dto.getTax())
                .finInvDiscount(dto.getDiscount())
                .finInvSecus(user)
                .finInvPaymentStatus(dto.getPaymentStatus())
                .finInvNet(BigDecimal.valueOf(105.0)) // (100 - 5) + 10
                .finInvCreatedAt(LocalDateTime.now())
                .finInvUpdatedAt(LocalDateTime.now())
                .build();

        when(companyRepo.findById(1)).thenReturn(Optional.of(company));
        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(invoiceRepo.save(any(Invoice.class))).thenReturn(savedInvoice);

        // Act
        InvoiceDTO result = service.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(dto.getTotal(), result.getTotal());
        assertEquals(dto.getTax(), result.getTax());
        assertEquals(dto.getDiscount(), result.getDiscount());
        assertEquals(BigDecimal.valueOf(105.0), result.getNet());
        
        verify(companyRepo).findById(1);
        verify(customerRepo).findById(1);
        verify(userRepo).findById(1);
        verify(invoiceRepo).save(any(Invoice.class));
    }
}

