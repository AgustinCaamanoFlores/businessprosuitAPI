package com.businessprosuite.api.controller.hr;

import com.businessprosuite.api.dto.hr.PayrollDTO;
import com.businessprosuite.api.service.hr.PayrollService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hr/payrolls")
public class PayrollController {

    private final PayrollService service;

    public PayrollController(PayrollService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PayrollDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PayrollDTO> create(@Valid @RequestBody PayrollDTO dto) {
        PayrollDTO created = service.create(dto);
        URI location = URI.create("/api/hr/payrolls/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PayrollDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
