package com.businessprosuite.api.impl.finance;

import com.businessprosuite.api.dto.finance.InvoiceDTO;
import com.businessprosuite.api.model.finance.Invoice;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.security.SecurityUser;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.repository.customer.CustomerRepository;
import com.businessprosuite.api.repository.finance.InvoiceRepository;
import com.businessprosuite.api.repository.security.SecurityUserRepository;
import com.businessprosuite.api.service.finance.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepo;
    private final ConfigCompanyRepository companyRepo;
    private final CustomerRepository       customerRepo;
    private final SecurityUserRepository   userRepo;

    @Override
    public List<InvoiceDTO> findAll() {
        return invoiceRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findById(Integer id) {
        Invoice inv = invoiceRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + id));
        return toDto(inv);
    }

    @Override
    public InvoiceDTO create(InvoiceDTO dto) {
        Company company = companyRepo.findById(dto.getConfigCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getConfigCompanyId()));
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + dto.getCustomerId()));
        SecurityUser user = userRepo.findById(dto.getSecurityUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getSecurityUserId()));

        Invoice inv = new Invoice();
        inv.setConfigCompany(company);
        inv.setFinInvDate(dto.getDate());
        inv.setFinInvCus(customer);
        inv.setFinInvTotal(dto.getTotal());
        inv.setFinInvTax(dto.getTax());
        inv.setFinInvDiscount(dto.getDiscount());
        inv.setFinInvSecus(user);
        inv.setFinInvPaymentStatus(dto.getPaymentStatus());
        inv.setFinInvCreatedAt(LocalDateTime.now());
        inv.setFinInvUpdatedAt(LocalDateTime.now());
        // Calcular neto: (total - discount) + tax
        BigDecimal net = dto.getTotal().subtract(dto.getDiscount()).add(dto.getTax());
        inv.setFinInvNet(net);

        Invoice saved = invoiceRepo.save(inv);
        return toDto(saved);
    }

    @Override
    public InvoiceDTO update(Integer id, InvoiceDTO dto) {
        Invoice inv = invoiceRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id " + id));
        Company company = companyRepo.findById(dto.getConfigCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getConfigCompanyId()));
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + dto.getCustomerId()));
        SecurityUser user = userRepo.findById(dto.getSecurityUserId())
                .orElseThrow(() -> new EntityNotFoundException("SecurityUser not found with id " + dto.getSecurityUserId()));

        inv.setConfigCompany(company);
        inv.setFinInvDate(dto.getDate());
        inv.setFinInvCus(customer);
        inv.setFinInvTotal(dto.getTotal());
        inv.setFinInvTax(dto.getTax());
        inv.setFinInvDiscount(dto.getDiscount());
        inv.setFinInvSecus(user);
        inv.setFinInvPaymentStatus(dto.getPaymentStatus());
        inv.setFinInvUpdatedAt(LocalDateTime.now());
        BigDecimal net = dto.getTotal().subtract(dto.getDiscount()).add(dto.getTax());
        inv.setFinInvNet(net);

        Invoice updated = invoiceRepo.save(inv);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!invoiceRepo.existsById(id)) {
            throw new EntityNotFoundException("Invoice not found with id " + id);
        }
        invoiceRepo.deleteById(id);
    }

    private InvoiceDTO toDto(Invoice inv) {
        return InvoiceDTO.builder()
                .id(inv.getId())
                .configCompanyId(inv.getConfigCompany().getId())
                .date(inv.getFinInvDate())
                .customerId(inv.getFinInvCus().getId())
                .total(inv.getFinInvTotal())
                .tax(inv.getFinInvTax())
                .discount(inv.getFinInvDiscount())
                .securityUserId(inv.getFinInvSecus().getId())
                .paymentStatus(inv.getFinInvPaymentStatus())
                .createdAt(inv.getFinInvCreatedAt())
                .updatedAt(inv.getFinInvUpdatedAt())
                .net(inv.getFinInvNet())
                .build();
    }
}
