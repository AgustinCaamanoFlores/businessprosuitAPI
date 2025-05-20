package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.dto.security.SecurityRolePermDTO;
import com.businessprosuite.api.service.security.SecurityRolePermService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-role-perms")
@RequiredArgsConstructor
public class SecurityRolePermController {

    private final SecurityRolePermService service;

    @GetMapping
    public ResponseEntity<List<SecurityRolePermDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityRolePermDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SecurityRolePermDTO> create(@RequestBody SecurityRolePermDTO dto) {
        SecurityRolePermDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityRolePermDTO> update(
            @PathVariable Integer id,
            @RequestBody SecurityRolePermDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
