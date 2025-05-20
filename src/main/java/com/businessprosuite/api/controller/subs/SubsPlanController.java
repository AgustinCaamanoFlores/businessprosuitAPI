package com.businessprosuite.api.controller.subs;

import com.businessprosuite.api.dto.subs.SubsPlanDTO;
import com.businessprosuite.api.service.subs.SubsPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subs-plans")
@RequiredArgsConstructor
public class SubsPlanController {

    private final SubsPlanService service;

    @GetMapping
    public ResponseEntity<List<SubsPlanDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubsPlanDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubsPlanDTO> create(@RequestBody SubsPlanDTO dto) {
        SubsPlanDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubsPlanDTO> update(
            @PathVariable Integer id,
            @RequestBody SubsPlanDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
