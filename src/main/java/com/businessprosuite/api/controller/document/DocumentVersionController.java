package com.businessprosuite.api.controller.document;

import com.businessprosuite.api.dto.document.DocumentVersionDTO;
import com.businessprosuite.api.service.document.DocumentVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document-versions")
@RequiredArgsConstructor
public class DocumentVersionController {

    private final DocumentVersionService service;

    @GetMapping
    public ResponseEntity<List<DocumentVersionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentVersionDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DocumentVersionDTO> create(@RequestBody DocumentVersionDTO dto) {
        DocumentVersionDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentVersionDTO> update(
            @PathVariable Integer id,
            @RequestBody DocumentVersionDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
