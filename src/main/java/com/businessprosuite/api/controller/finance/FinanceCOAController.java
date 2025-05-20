package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.dto.finance.FinanceCOADTO;
import com.businessprosuite.api.service.finance.FinanceCOAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance-coas")
@RequiredArgsConstructor
public class FinanceCOAController {

    private final FinanceCOAService service;

    @GetMapping
    public ResponseEntity<List<FinanceCOADTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceCOADTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<FinanceCOADTO> create(@RequestBody FinanceCOADTO dto) {
        FinanceCOADTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanceCOADTO> update(
            @PathVariable Integer id,
            @RequestBody FinanceCOADTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
