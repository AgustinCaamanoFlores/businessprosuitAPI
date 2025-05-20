package com.businessprosuite.api.controller.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowDefinitionDTO;
import com.businessprosuite.api.service.workflow.WorkflowDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflow-definitions")
@RequiredArgsConstructor
public class WorkflowDefinitionController {

    private final WorkflowDefinitionService service;

    @GetMapping
    public ResponseEntity<List<WorkflowDefinitionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowDefinitionDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<WorkflowDefinitionDTO> create(@RequestBody WorkflowDefinitionDTO dto) {
        WorkflowDefinitionDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowDefinitionDTO> update(
            @PathVariable Integer id,
            @RequestBody WorkflowDefinitionDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
