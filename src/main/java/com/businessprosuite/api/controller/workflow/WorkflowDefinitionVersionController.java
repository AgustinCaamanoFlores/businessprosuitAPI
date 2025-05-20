package com.businessprosuite.api.controller.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowDefinitionVersionDTO;
import com.businessprosuite.api.service.workflow.WorkflowDefinitionVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflow-definition-versions")
@RequiredArgsConstructor
public class WorkflowDefinitionVersionController {

    private final WorkflowDefinitionVersionService service;

    @GetMapping
    public ResponseEntity<List<WorkflowDefinitionVersionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowDefinitionVersionDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<WorkflowDefinitionVersionDTO> create(@RequestBody WorkflowDefinitionVersionDTO dto) {
        WorkflowDefinitionVersionDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowDefinitionVersionDTO> update(
            @PathVariable Integer id,
            @RequestBody WorkflowDefinitionVersionDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
