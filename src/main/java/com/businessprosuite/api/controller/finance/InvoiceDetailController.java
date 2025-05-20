package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.dto.finance.InvoiceDetailDTO;
import com.businessprosuite.api.service.finance.InvoiceDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice-details")
@RequiredArgsConstructor
public class InvoiceDetailController {

    private final InvoiceDetailService service;

    @GetMapping
    public ResponseEntity<List<InvoiceDetailDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDetailDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<InvoiceDetailDTO> create(@RequestBody InvoiceDetailDTO dto) {
        InvoiceDetailDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDetailDTO> update(
            @PathVariable Integer id,
            @RequestBody InvoiceDetailDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
