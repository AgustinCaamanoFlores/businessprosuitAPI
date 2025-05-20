package com.businessprosuite.api.controller.analytics;

import com.businessprosuite.api.dto.analytics.KpiDefDTO;
import com.businessprosuite.api.service.analytics.KpiDefService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kpi-defs")
@RequiredArgsConstructor
public class KpiDefController {

    private final KpiDefService service;

    @GetMapping
    public ResponseEntity<List<KpiDefDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KpiDefDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<KpiDefDTO> create(@RequestBody KpiDefDTO dto) {
        KpiDefDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KpiDefDTO> update(
            @PathVariable Integer id,
            @RequestBody KpiDefDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
