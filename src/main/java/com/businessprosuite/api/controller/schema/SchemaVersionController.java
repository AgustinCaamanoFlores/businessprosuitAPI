package com.businessprosuite.api.controller.schema;

import com.businessprosuite.api.dto.schema.SchemaVersionDTO;
import com.businessprosuite.api.service.schema.SchemaVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schema-versions")
@RequiredArgsConstructor
public class SchemaVersionController {

    private final SchemaVersionService service;

    @GetMapping
    public ResponseEntity<List<SchemaVersionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchemaVersionDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SchemaVersionDTO> create(@RequestBody SchemaVersionDTO dto) {
        SchemaVersionDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchemaVersionDTO> update(
            @PathVariable Integer id,
            @RequestBody SchemaVersionDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
