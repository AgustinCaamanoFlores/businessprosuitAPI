package com.businessprosuite.api.controller.analytics;

import com.businessprosuite.api.dto.analytics.MetricDTO;
import com.businessprosuite.api.service.analytics.MetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricController {

    private final MetricService service;

    @GetMapping
    public ResponseEntity<List<MetricDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetricDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MetricDTO> create(@RequestBody MetricDTO dto) {
        MetricDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetricDTO> update(
            @PathVariable Integer id,
            @RequestBody MetricDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
