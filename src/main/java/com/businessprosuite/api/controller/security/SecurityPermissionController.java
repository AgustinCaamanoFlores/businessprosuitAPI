package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.dto.security.SecurityPermissionDTO;
import com.businessprosuite.api.service.security.SecurityPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-permissions")
@RequiredArgsConstructor
public class SecurityPermissionController {

    private final SecurityPermissionService service;

    @GetMapping
    public ResponseEntity<List<SecurityPermissionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityPermissionDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SecurityPermissionDTO> create(@RequestBody SecurityPermissionDTO dto) {
        SecurityPermissionDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityPermissionDTO> update(
            @PathVariable Integer id,
            @RequestBody SecurityPermissionDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
