package com.businessprosuite.api.controller.user;

import com.businessprosuite.api.dto.user.UserConsentDTO;
import com.businessprosuite.api.service.user.UserConsentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/consents")
public class ConsentController {
    private final UserConsentService service;

    public ConsentController(UserConsentService service) {
        this.service = service;
    }

    @GetMapping public ResponseEntity<List<UserConsentDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/{id}") public ResponseEntity<UserConsentDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @PostMapping public ResponseEntity<UserConsentDTO> create(
            @Valid @RequestBody UserConsentDTO dto) {
        UserConsentDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/consents/" + created.getId()))
                .body(created);
    }
    @PutMapping("/{id}") public ResponseEntity<UserConsentDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserConsentDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(
            @PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
