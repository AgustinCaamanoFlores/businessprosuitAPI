package com.businessprosuite.api.controller.inventory;

import com.businessprosuite.api.dto.inventory.InventoryCategoryDTO;
import com.businessprosuite.api.service.inventory.InventoryCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-categories")
@RequiredArgsConstructor
public class InventoryCategoryController {

    private final InventoryCategoryService service;

    @GetMapping
    public ResponseEntity<List<InventoryCategoryDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryCategoryDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InventoryCategoryDTO> create(@RequestBody InventoryCategoryDTO dto) {
        InventoryCategoryDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryCategoryDTO> update(
            @PathVariable Integer id,
            @RequestBody InventoryCategoryDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
