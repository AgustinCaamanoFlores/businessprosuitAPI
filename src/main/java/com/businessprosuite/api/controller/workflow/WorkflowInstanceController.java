package com.businessprosuite.api.controller.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowInstanceDTO;
import com.businessprosuite.api.service.workflow.WorkflowInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflow-instances")
@RequiredArgsConstructor
public class WorkflowInstanceController {

    private final WorkflowInstanceService service;

    @GetMapping
    public ResponseEntity<List<WorkflowInstanceDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowInstanceDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<WorkflowInstanceDTO> create(@RequestBody WorkflowInstanceDTO dto) {
        WorkflowInstanceDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowInstanceDTO> update(
            @PathVariable Integer id,
            @RequestBody WorkflowInstanceDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
