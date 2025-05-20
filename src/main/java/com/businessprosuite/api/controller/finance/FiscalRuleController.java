package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.dto.finance.FiscalRuleDTO;
import com.businessprosuite.api.service.finance.FiscalRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fiscal-rules")
@RequiredArgsConstructor
public class FiscalRuleController {

    private final FiscalRuleService service;

    @GetMapping
    public ResponseEntity<List<FiscalRuleDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FiscalRuleDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<FiscalRuleDTO> create(@RequestBody FiscalRuleDTO dto) {
        FiscalRuleDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FiscalRuleDTO> update(
            @PathVariable Integer id,
            @RequestBody FiscalRuleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}