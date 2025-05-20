package com.businessprosuite.api.controller.leasing;

import com.businessprosuite.api.dto.leasing.LeasingPaymentDTO;
import com.businessprosuite.api.service.leasing.LeasingPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leasing-payments")
@RequiredArgsConstructor
public class LeasingPaymentController {

    private final LeasingPaymentService service;

    @GetMapping
    public ResponseEntity<List<LeasingPaymentDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeasingPaymentDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<LeasingPaymentDTO> create(@RequestBody LeasingPaymentDTO dto) {
        LeasingPaymentDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeasingPaymentDTO> update(
            @PathVariable Integer id,
            @RequestBody LeasingPaymentDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
