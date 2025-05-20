package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.dto.finance.PeriodDTO;
import com.businessprosuite.api.service.finance.PeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/periods")
@RequiredArgsConstructor
public class PeriodController {

    private final PeriodService service;

    @GetMapping
    public ResponseEntity<List<PeriodDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PeriodDTO> create(@RequestBody PeriodDTO dto) {
        PeriodDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodDTO> update(
            @PathVariable Integer id,
            @RequestBody PeriodDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
