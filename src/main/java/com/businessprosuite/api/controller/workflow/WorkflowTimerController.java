package com.businessprosuite.api.controller.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowTimerDTO;
import com.businessprosuite.api.service.workflow.WorkflowTimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflow-timers")
@RequiredArgsConstructor
public class WorkflowTimerController {

    private final WorkflowTimerService service;

    @GetMapping
    public ResponseEntity<List<WorkflowTimerDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowTimerDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<WorkflowTimerDTO> create(@RequestBody WorkflowTimerDTO dto) {
        WorkflowTimerDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowTimerDTO> update(
            @PathVariable Integer id,
            @RequestBody WorkflowTimerDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
