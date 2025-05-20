package com.businessprosuite.api.controller.audit;

import com.businessprosuite.api.dto.audit.ActivityLogDTO;
import com.businessprosuite.api.service.audit.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-logs")
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogService service;

    @GetMapping
    public ResponseEntity<List<ActivityLogDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLogDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ActivityLogDTO> create(@RequestBody ActivityLogDTO dto) {
        ActivityLogDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityLogDTO> update(
            @PathVariable Integer id,
            @RequestBody ActivityLogDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
