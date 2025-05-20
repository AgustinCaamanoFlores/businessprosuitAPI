package com.businessprosuite.api.controller.analytics;

import com.businessprosuite.api.dto.analytics.KpiValorDTO;
import com.businessprosuite.api.service.analytics.KpiValorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kpi-valors")
@RequiredArgsConstructor
public class KpiValorController {

    private final KpiValorService service;

    @GetMapping
    public ResponseEntity<List<KpiValorDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KpiValorDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<KpiValorDTO> create(@RequestBody KpiValorDTO dto) {
        KpiValorDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KpiValorDTO> update(
            @PathVariable Integer id,
            @RequestBody KpiValorDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
