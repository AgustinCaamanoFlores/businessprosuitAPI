package com.businessprosuite.api.controller.user;

import com.businessprosuite.api.dto.user.UserConsentDTO;
import com.businessprosuite.api.service.user.UserConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-consents")
@RequiredArgsConstructor
public class UserConsentController {

    private final UserConsentService consentService;

    @GetMapping
    public ResponseEntity<List<UserConsentDTO>> getAll() {
        return ResponseEntity.ok(consentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserConsentDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(consentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserConsentDTO> create(@RequestBody UserConsentDTO dto) {
        UserConsentDTO created = consentService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserConsentDTO> update(
            @PathVariable Integer id,
            @RequestBody UserConsentDTO dto) {
        return ResponseEntity.ok(consentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        consentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
