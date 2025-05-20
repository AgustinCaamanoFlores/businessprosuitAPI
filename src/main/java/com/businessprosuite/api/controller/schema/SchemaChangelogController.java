package com.businessprosuite.api.controller.schema;

import com.businessprosuite.api.dto.schema.SchemaChangelogDTO;
import com.businessprosuite.api.service.schema.SchemaChangelogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schema-changelog")
@RequiredArgsConstructor
public class SchemaChangelogController {

    private final SchemaChangelogService service;

    @GetMapping
    public ResponseEntity<List<SchemaChangelogDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchemaChangelogDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SchemaChangelogDTO> create(@RequestBody SchemaChangelogDTO dto) {
        SchemaChangelogDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchemaChangelogDTO> update(
            @PathVariable Integer id,
            @RequestBody SchemaChangelogDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
