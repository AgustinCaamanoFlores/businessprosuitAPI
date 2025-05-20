package com.businessprosuite.api.controller.inventory;

import com.businessprosuite.api.dto.inventory.InventoryReorderRuleDTO;
import com.businessprosuite.api.service.inventory.InventoryReorderRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-reorder-rules")
@RequiredArgsConstructor
public class InventoryReorderRuleController {

    private final InventoryReorderRuleService service;

    @GetMapping
    public ResponseEntity<List<InventoryReorderRuleDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryReorderRuleDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InventoryReorderRuleDTO> create(@RequestBody InventoryReorderRuleDTO dto) {
        InventoryReorderRuleDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryReorderRuleDTO> update(
            @PathVariable Integer id,
            @RequestBody InventoryReorderRuleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
