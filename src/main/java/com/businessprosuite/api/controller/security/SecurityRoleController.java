package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.dto.security.SecurityRoleDTO;
import com.businessprosuite.api.service.security.SecurityRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-roles")
@RequiredArgsConstructor
public class SecurityRoleController {

    private final SecurityRoleService service;

    @GetMapping
    public ResponseEntity<List<SecurityRoleDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityRoleDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SecurityRoleDTO> create(@RequestBody SecurityRoleDTO dto) {
        SecurityRoleDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityRoleDTO> update(
            @PathVariable Integer id,
            @RequestBody SecurityRoleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
