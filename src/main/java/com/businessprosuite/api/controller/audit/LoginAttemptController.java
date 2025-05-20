package com.businessprosuite.api.controller.audit;

import com.businessprosuite.api.dto.audit.LoginAttemptDTO;
import com.businessprosuite.api.service.audit.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/login-attempts")
@RequiredArgsConstructor
public class LoginAttemptController {

    private final LoginAttemptService service;

    @GetMapping
    public ResponseEntity<List<LoginAttemptDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoginAttemptDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<LoginAttemptDTO> create(@RequestBody LoginAttemptDTO dto) {
        LoginAttemptDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoginAttemptDTO> update(
            @PathVariable Integer id,
            @RequestBody LoginAttemptDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
