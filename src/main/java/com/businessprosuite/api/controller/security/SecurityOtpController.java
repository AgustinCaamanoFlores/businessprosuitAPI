package com.businessprosuite.api.controller.security;

import com.businessprosuite.api.dto.security.SecurityOtpDTO;
import com.businessprosuite.api.service.security.SecurityOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-otps")
@RequiredArgsConstructor
public class SecurityOtpController {

    private final SecurityOtpService service;

    @GetMapping
    public ResponseEntity<List<SecurityOtpDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityOtpDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SecurityOtpDTO> create(@RequestBody SecurityOtpDTO dto) {
        SecurityOtpDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityOtpDTO> update(
            @PathVariable Integer id,
            @RequestBody SecurityOtpDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
