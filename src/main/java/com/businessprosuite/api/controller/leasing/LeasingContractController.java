package com.businessprosuite.api.controller.leasing;

import com.businessprosuite.api.dto.leasing.LeasingContractDTO;
import com.businessprosuite.api.service.leasing.LeasingContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leasing-contracts")
@RequiredArgsConstructor
public class LeasingContractController {

    private final LeasingContractService service;

    @GetMapping
    public ResponseEntity<List<LeasingContractDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeasingContractDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<LeasingContractDTO> create(@RequestBody LeasingContractDTO dto) {
        LeasingContractDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeasingContractDTO> update(
            @PathVariable Integer id,
            @RequestBody LeasingContractDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
