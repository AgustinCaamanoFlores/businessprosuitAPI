package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.dto.security.SecurityPasswordPolicyDTO;
import com.businessprosuite.api.service.security.SecurityPasswordPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-password-policies")
@RequiredArgsConstructor
public class SecurityPasswordPolicyController {

    private final SecurityPasswordPolicyService service;

    @GetMapping
    public ResponseEntity<List<SecurityPasswordPolicyDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityPasswordPolicyDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SecurityPasswordPolicyDTO> create(@RequestBody SecurityPasswordPolicyDTO dto) {
        SecurityPasswordPolicyDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityPasswordPolicyDTO> update(
            @PathVariable Integer id,
            @RequestBody SecurityPasswordPolicyDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
