package com.businessprosuite.api.controller.core;

import com.businessprosuite.api.dto.core.TranslationDTO;
import com.businessprosuite.api.service.core.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-translations")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService service;

    @GetMapping
    public ResponseEntity<List<TranslationDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranslationDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TranslationDTO> create(@RequestBody TranslationDTO dto) {
        TranslationDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TranslationDTO> update(
            @PathVariable Integer id,
            @RequestBody TranslationDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
