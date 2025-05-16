package com.businessprosuite.api.controller;

import com.businessprosuite.api.dto.CustomerDto;
import com.businessprosuite.api.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable Long id) {
        return service.findAll().stream()
                .filter(dto -> dto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@RequestBody CustomerDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public CustomerDto update(@PathVariable Long id, @RequestBody CustomerDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}