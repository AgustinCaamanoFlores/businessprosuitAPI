package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.dto.finance.CurrencyDTO;
import com.businessprosuite.api.service.finance.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService service;

    @GetMapping
    public ResponseEntity<List<CurrencyDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CurrencyDTO> create(@RequestBody CurrencyDTO dto) {
        CurrencyDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyDTO> update(
            @PathVariable Integer id,
            @RequestBody CurrencyDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
