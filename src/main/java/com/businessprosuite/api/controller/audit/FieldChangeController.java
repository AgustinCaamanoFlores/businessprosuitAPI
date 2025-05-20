package com.businessprosuite.api.controller.audit;

import com.businessprosuite.api.dto.audit.FieldChangeDTO;
import com.businessprosuite.api.service.audit.FieldChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/field-changes")
@RequiredArgsConstructor
public class FieldChangeController {

    private final FieldChangeService service;

    @GetMapping
    public ResponseEntity<List<FieldChangeDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldChangeDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<FieldChangeDTO> create(@RequestBody FieldChangeDTO dto) {
        FieldChangeDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldChangeDTO> update(
            @PathVariable Integer id,
            @RequestBody FieldChangeDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
