package com.businessprosuite.api.controller.analytics;

import com.businessprosuite.api.dto.analytics.DwTimeDTO;
import com.businessprosuite.api.service.analytics.DwTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dw-time")
@RequiredArgsConstructor
public class DwTimeController {

    private final DwTimeService service;

    @GetMapping
    public ResponseEntity<List<DwTimeDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{date}")
    public ResponseEntity<DwTimeDTO> getByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.findByDate(date));
    }

    @PostMapping
    public ResponseEntity<DwTimeDTO> create(@RequestBody DwTimeDTO dto) {
        DwTimeDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{date}")
    public ResponseEntity<DwTimeDTO> update(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody DwTimeDTO dto) {
        return ResponseEntity.ok(service.update(date, dto));
    }

    @DeleteMapping("/{date}")
    public ResponseEntity<Void> delete(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        service.delete(date);
        return ResponseEntity.noContent().build();
    }
}
