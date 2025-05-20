package com.businessprosuite.api.controller.subs;

import com.businessprosuite.api.dto.subs.SubsSuscriptionDTO;
import com.businessprosuite.api.service.subs.SubsSuscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subs-suscriptions")
@RequiredArgsConstructor
public class SubsSuscriptionController {

    private final SubsSuscriptionService service;

    @GetMapping
    public ResponseEntity<List<SubsSuscriptionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubsSuscriptionDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubsSuscriptionDTO> create(@RequestBody SubsSuscriptionDTO dto) {
        SubsSuscriptionDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubsSuscriptionDTO> update(
            @PathVariable Integer id,
            @RequestBody SubsSuscriptionDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
