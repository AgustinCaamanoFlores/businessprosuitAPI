package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.dto.security.SecurityAttributeDTO;
import com.businessprosuite.api.service.security.SecurityAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-attributes")
@RequiredArgsConstructor
public class SecurityAttributeController {

    private final SecurityAttributeService service;

    @GetMapping
    public ResponseEntity<List<SecurityAttributeDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityAttributeDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SecurityAttributeDTO> create(@RequestBody SecurityAttributeDTO dto) {
        SecurityAttributeDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityAttributeDTO> update(
            @PathVariable Integer id,
            @RequestBody SecurityAttributeDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
