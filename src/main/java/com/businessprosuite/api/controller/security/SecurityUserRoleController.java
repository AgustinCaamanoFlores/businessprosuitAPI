package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.dto.security.SecurityUserRoleDTO;
import com.businessprosuite.api.service.security.SecurityUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-user-roles")
@RequiredArgsConstructor
public class SecurityUserRoleController {

    private final SecurityUserRoleService service;

    @GetMapping
    public ResponseEntity<List<SecurityUserRoleDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityUserRoleDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SecurityUserRoleDTO> create(@RequestBody SecurityUserRoleDTO dto) {
        SecurityUserRoleDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityUserRoleDTO> update(
            @PathVariable Integer id,
            @RequestBody SecurityUserRoleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
