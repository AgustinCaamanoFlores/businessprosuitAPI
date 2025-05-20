package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.dto.security.SecurityPasswordHistoryDTO;
import com.businessprosuite.api.service.security.SecurityPasswordHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-password-history")
@RequiredArgsConstructor
public class SecurityPasswordHistoryController {

    private final SecurityPasswordHistoryService service;

    @GetMapping
    public ResponseEntity<List<SecurityPasswordHistoryDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityPasswordHistoryDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SecurityPasswordHistoryDTO> create(@RequestBody SecurityPasswordHistoryDTO dto) {
        SecurityPasswordHistoryDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityPasswordHistoryDTO> update(
            @PathVariable Integer id,
            @RequestBody SecurityPasswordHistoryDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
