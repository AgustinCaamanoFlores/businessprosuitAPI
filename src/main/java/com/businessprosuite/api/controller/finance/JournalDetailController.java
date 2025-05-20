package com.businessprosuite.api.controller.finance;

import com.businessprosuite.api.dto.finance.JournalDetailDTO;
import com.businessprosuite.api.service.finance.JournalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal-details")
@RequiredArgsConstructor
public class JournalDetailController {

    private final JournalDetailService service;

    @GetMapping
    public ResponseEntity<List<JournalDetailDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalDetailDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<JournalDetailDTO> create(@RequestBody JournalDetailDTO dto) {
        JournalDetailDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalDetailDTO> update(
            @PathVariable Integer id,
            @RequestBody JournalDetailDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
