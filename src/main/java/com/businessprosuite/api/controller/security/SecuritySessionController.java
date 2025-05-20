package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.dto.security.SecuritySessionDTO;
import com.businessprosuite.api.service.security.SecuritySessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-sessions")
@RequiredArgsConstructor
public class SecuritySessionController {

    private final SecuritySessionService service;

    @GetMapping
    public ResponseEntity<List<SecuritySessionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecuritySessionDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SecuritySessionDTO> create(@RequestBody SecuritySessionDTO dto) {
        SecuritySessionDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecuritySessionDTO> update(
            @PathVariable String id,
            @RequestBody SecuritySessionDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
