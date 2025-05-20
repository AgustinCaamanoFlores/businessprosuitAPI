package com.businessprosuite.api.controller.analytics;

import com.businessprosuite.api.dto.analytics.DataRetentionRuleDTO;
import com.businessprosuite.api.service.analytics.DataRetentionRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data-retention-rules")
@RequiredArgsConstructor
public class DataRetentionRuleController {

    private final DataRetentionRuleService service;

    @GetMapping
    public ResponseEntity<List<DataRetentionRuleDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataRetentionRuleDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DataRetentionRuleDTO> create(@RequestBody DataRetentionRuleDTO dto) {
        DataRetentionRuleDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataRetentionRuleDTO> update(
            @PathVariable Integer id,
            @RequestBody DataRetentionRuleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
