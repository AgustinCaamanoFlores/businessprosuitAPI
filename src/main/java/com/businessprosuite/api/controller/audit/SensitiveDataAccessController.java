package com.businessprosuite.api.controller.audit;

import com.businessprosuite.api.dto.audit.SensitiveDataAccessDTO;
import com.businessprosuite.api.service.audit.SensitiveDataAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensitive-data-access")
@RequiredArgsConstructor
public class SensitiveDataAccessController {

    private final SensitiveDataAccessService service;

    @GetMapping
    public ResponseEntity<List<SensitiveDataAccessDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensitiveDataAccessDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SensitiveDataAccessDTO> create(@RequestBody SensitiveDataAccessDTO dto) {
        SensitiveDataAccessDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensitiveDataAccessDTO> update(
            @PathVariable Integer id,
            @RequestBody SensitiveDataAccessDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
