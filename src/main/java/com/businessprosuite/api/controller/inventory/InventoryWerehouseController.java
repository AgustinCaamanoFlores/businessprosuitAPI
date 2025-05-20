package com.businessprosuite.api.controller.inventory;

import com.businessprosuite.api.dto.inventory.InventoryWerehouseDTO;
import com.businessprosuite.api.service.inventory.InventoryWerehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-warehouses")
@RequiredArgsConstructor
public class InventoryWerehouseController {

    private final InventoryWerehouseService service;

    @GetMapping
    public ResponseEntity<List<InventoryWerehouseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryWerehouseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InventoryWerehouseDTO> create(@RequestBody InventoryWerehouseDTO dto) {
        InventoryWerehouseDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryWerehouseDTO> update(
            @PathVariable Integer id,
            @RequestBody InventoryWerehouseDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
