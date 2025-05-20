package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.dto.finance.TaxRateDTO;
import com.businessprosuite.api.service.finance.TaxRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tax-rates")
@RequiredArgsConstructor
public class TaxRateController {

    private final TaxRateService service;

    @GetMapping
    public ResponseEntity<List<TaxRateDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxRateDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TaxRateDTO> create(@RequestBody TaxRateDTO dto) {
        TaxRateDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxRateDTO> update(
            @PathVariable Integer id,
            @RequestBody TaxRateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
