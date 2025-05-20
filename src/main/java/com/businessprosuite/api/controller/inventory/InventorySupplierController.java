package com.businessprosuite.api.controller.inventory;

import com.businessprosuite.api.dto.inventory.InventorySupplierDTO;
import com.businessprosuite.api.service.inventory.InventorySupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-suppliers")
@RequiredArgsConstructor
public class InventorySupplierController {

    private final InventorySupplierService service;

    @GetMapping
    public ResponseEntity<List<InventorySupplierDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventorySupplierDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InventorySupplierDTO> create(@RequestBody InventorySupplierDTO dto) {
        InventorySupplierDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventorySupplierDTO> update(
            @PathVariable Integer id,
            @RequestBody InventorySupplierDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}