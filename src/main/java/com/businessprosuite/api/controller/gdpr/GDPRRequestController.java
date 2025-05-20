package com.businessprosuite.api.controller.gdpr;

import com.businessprosuite.api.dto.gdpr.GDPRRequestDTO;
import com.businessprosuite.api.service.gdpr.GDPRRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gdpr-requests")
@RequiredArgsConstructor
public class GDPRRequestController {

    private final GDPRRequestService service;

    @GetMapping
    public ResponseEntity<List<GDPRRequestDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GDPRRequestDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<GDPRRequestDTO> create(@RequestBody GDPRRequestDTO dto) {
        GDPRRequestDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GDPRRequestDTO> update(
            @PathVariable Integer id,
            @RequestBody GDPRRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
