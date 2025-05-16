package com.businessprosuite.api.service;

import com.businessprosuite.api.dto.CustomerDto;
import com.businessprosuite.api.mapper.CustomerMapper;
import com.businessprosuite.api.model.Customer;
import com.businessprosuite.api.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository repo;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository repo, CustomerMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<CustomerDto> findAll() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDto create(CustomerDto dto) {
        Customer entity = mapper.toEntity(dto);
        Customer saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public CustomerDto update(Long id, CustomerDto dto) {
        Customer existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        Customer toSave = mapper.toEntity(dto);
        toSave.setId(existing.getId());
        return mapper.toDto(repo.save(toSave));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}