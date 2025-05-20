package com.businessprosuite.api.controller.analytics;

import com.businessprosuite.api.dto.analytics.MatViewSaleDTO;
import com.businessprosuite.api.model.analytics.MatViewSaleId;
import com.businessprosuite.api.service.analytics.MatViewSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mat-view-sales")
@RequiredArgsConstructor
public class MatViewSaleController {

    private final MatViewSaleService service;

    @GetMapping
    public ResponseEntity<List<MatViewSaleDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/id")
    public ResponseEntity<MatViewSaleDTO> getById(@RequestBody MatViewSaleId id) {
        MatViewSaleDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<MatViewSaleDTO> create(@RequestBody MatViewSaleDTO dto) {
        MatViewSaleDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/id")
    public ResponseEntity<MatViewSaleDTO> update(
            @RequestBody MatViewSaleId id,
            @RequestBody MatViewSaleDTO dto) {
        MatViewSaleDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> delete(@RequestBody MatViewSaleId id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
