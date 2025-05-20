package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.dto.security.SecurityPolicyRuleDTO;
import com.businessprosuite.api.service.security.SecurityPolicyRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-policy-rules")
@RequiredArgsConstructor
public class SecurityPolicyRuleController {

    private final SecurityPolicyRuleService service;

    @GetMapping
    public ResponseEntity<List<SecurityPolicyRuleDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityPolicyRuleDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SecurityPolicyRuleDTO> create(@RequestBody SecurityPolicyRuleDTO dto) {
        SecurityPolicyRuleDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityPolicyRuleDTO> update(
            @PathVariable Integer id,
            @RequestBody SecurityPolicyRuleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
