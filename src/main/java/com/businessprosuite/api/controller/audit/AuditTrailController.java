package com.businessprosuite.api.controller.audit;

import com.businessprosuite.api.dto.audit.AuditTrailDTO;
import com.businessprosuite.api.model.audit.AuditTrailId;
import com.businessprosuite.api.service.audit.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-trails")
@RequiredArgsConstructor
public class AuditTrailController {

    private final AuditTrailService service;

    @GetMapping
    public ResponseEntity<List<AuditTrailDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{table}/{record}/{changedAt}")
    public ResponseEntity<AuditTrailDTO> getById(
            @PathVariable String table,
            @PathVariable Integer record,
            @PathVariable String changedAt) {
        AuditTrailId id = new AuditTrailId(table, record, java.time.LocalDateTime.parse(changedAt));
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<AuditTrailDTO> create(@RequestBody AuditTrailDTO dto) {
        AuditTrailDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{table}/{record}/{changedAt}")
    public ResponseEntity<AuditTrailDTO> update(
            @PathVariable String table,
            @PathVariable Integer record,
            @PathVariable String changedAt,
            @RequestBody AuditTrailDTO dto) {
        AuditTrailId id = new AuditTrailId(table, record, java.time.LocalDateTime.parse(changedAt));
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{table}/{record}/{changedAt}")
    public ResponseEntity<Void> delete(
            @PathVariable String table,
            @PathVariable Integer record,
            @PathVariable String changedAt) {
        AuditTrailId id = new AuditTrailId(table, record, java.time.LocalDateTime.parse(changedAt));
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
