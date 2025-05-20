package com.businessprosuite.api.impl.customer;

import com.businessprosuite.api.dto.customer.CustomerDTO;
import com.businessprosuite.api.model.customer.Customer;
import com.businessprosuite.api.model.company.Company;
import com.businessprosuite.api.model.config.ConfigCompany;
import com.businessprosuite.api.model.config.ConfigCountry;
import com.businessprosuite.api.repository.customer.CustomerRepository;
import com.businessprosuite.api.repository.config.ConfigCompanyRepository;
import com.businessprosuite.api.repository.company.CompanyRepository;
import com.businessprosuite.api.repository.config.ConfigCountryRepository;
import com.businessprosuite.api.service.customer.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;
    private final ConfigCompanyRepository configCompanyRepo;
    private final CompanyRepository companyRepo;
    private final ConfigCountryRepository countryRepo;

    @Override
    public List<CustomerDTO> findAll() {
        return customerRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findById(Integer id) {
        Customer cust = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
        return toDto(cust);
    }

    @Override
    public CustomerDTO create(CustomerDTO dto) {
        ConfigCompany cfg = configCompanyRepo.findById(dto.getConfigCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getConfigCompanyId()));
        Company cmp = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));
        ConfigCountry country = countryRepo.findById(dto.getCountryCode())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCountry not found with code " + dto.getCountryCode()));

        Customer cust = new Customer();
        cust.setConfigCompany(cfg);
        cust.setCusName(dto.getName());
        cust.setCusEmail(dto.getEmail());
        cust.setCusPhone(dto.getPhone());
        cust.setCusAddress(dto.getAddress());
        cust.setCusTaxId(dto.getTaxId());
        cust.setCusCmp(cmp);
        cust.setCusConfigCountryCodigo(country);
        cust.setCusRegDate(dto.getRegDate() != null ? dto.getRegDate() : LocalDateTime.now());
        cust.setCusCreatedAt(LocalDateTime.now());
        cust.setCusUpdatedAt(LocalDateTime.now());

        Customer saved = customerRepo.save(cust);
        return toDto(saved);
    }

    @Override
    public CustomerDTO update(Integer id, CustomerDTO dto) {
        Customer cust = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
        ConfigCompany cfg = configCompanyRepo.findById(dto.getConfigCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCompany not found with id " + dto.getConfigCompanyId()));
        Company cmp = companyRepo.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId()));
        ConfigCountry country = countryRepo.findById(dto.getCountryCode())
                .orElseThrow(() -> new EntityNotFoundException("ConfigCountry not found with code " + dto.getCountryCode()));

        cust.setConfigCompany(cfg);
        cust.setCusName(dto.getName());
        cust.setCusEmail(dto.getEmail());
        cust.setCusPhone(dto.getPhone());
        cust.setCusAddress(dto.getAddress());
        cust.setCusTaxId(dto.getTaxId());
        cust.setCusCmp(cmp);
        cust.setCusConfigCountryCodigo(country);
        cust.setCusUpdatedAt(LocalDateTime.now());

        Customer updated = customerRepo.save(cust);
        return toDto(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!customerRepo.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id " + id);
        }
        customerRepo.deleteById(id);
    }

    private CustomerDTO toDto(Customer cust) {
        return CustomerDTO.builder()
                .id(cust.getId())
                .configCompanyId(cust.getConfigCompany().getId())
                .name(cust.getCusName())
                .email(cust.getCusEmail())
                .phone(cust.getCusPhone())
                .address(cust.getCusAddress())
                .taxId(cust.getCusTaxId())
                .companyId(cust.getCusCmp().getId())
                .countryCode(cust.getCusConfigCountryCodigo().getCodigo())
                .regDate(cust.getCusRegDate())
                .createdAt(cust.getCusCreatedAt())
                .updatedAt(cust.getCusUpdatedAt())
                .build();
    }
}