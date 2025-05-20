package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.dto.finance.FinanceConsReportDTO;
import com.businessprosuite.api.service.finance.FinanceConsReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance-cons-reports")
@RequiredArgsConstructor
public class FinanceConsReportController {

    private final FinanceConsReportService service;

    @GetMapping
    public ResponseEntity<List<FinanceConsReportDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceConsReportDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<FinanceConsReportDTO> create(@RequestBody FinanceConsReportDTO dto) {
        FinanceConsReportDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanceConsReportDTO> update(
            @PathVariable Integer id,
            @RequestBody FinanceConsReportDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}