package com.businessprosuite.api.controller.analytics;

import com.businessprosuite.api.dto.analytics.DwSaleDTO;
import com.businessprosuite.api.model.analytics.DwSaleId;
import com.businessprosuite.api.service.analytics.DwSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dw-sales")
@RequiredArgsConstructor
public class DwSaleController {

    private final DwSaleService service;

    @GetMapping
    public ResponseEntity<List<DwSaleDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/id")
    public ResponseEntity<DwSaleDTO> getById(@RequestBody DwSaleId id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DwSaleDTO> create(@RequestBody DwSaleDTO dto) {
        DwSaleDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/id")
    public ResponseEntity<DwSaleDTO> update(
            @RequestBody DwSaleId id,
            @RequestBody DwSaleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> delete(@RequestBody DwSaleId id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
