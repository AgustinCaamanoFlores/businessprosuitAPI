package com.businessprosuite.api.controller.inventory;

import com.businessprosuite.api.dto.inventory.InventoryWarehouseDTO;
import com.businessprosuite.api.service.inventory.InventoryWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-warehouses")
@RequiredArgsConstructor
public class InventoryWarehouseController {

    private final InventoryWarehouseService service;

    @GetMapping
    public ResponseEntity<List<InventoryWarehouseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryWarehouseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InventoryWarehouseDTO> create(@RequestBody InventoryWarehouseDTO dto) {
        InventoryWarehouseDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryWarehouseDTO> update(
            @PathVariable Integer id,
            @RequestBody InventoryWarehouseDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
