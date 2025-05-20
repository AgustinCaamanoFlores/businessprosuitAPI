package com.businessprosuite.api.controller.workflow;

import com.businessprosuite.api.dto.workflow.WorkflowAssignmentRuleDTO;
import com.businessprosuite.api.service.workflow.WorkflowAssignmentRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflow-assignment-rules")
@RequiredArgsConstructor
public class WorkflowAssignmentRuleController {

    private final WorkflowAssignmentRuleService service;

    @GetMapping
    public ResponseEntity<List<WorkflowAssignmentRuleDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowAssignmentRuleDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<WorkflowAssignmentRuleDTO> create(@RequestBody WorkflowAssignmentRuleDTO dto) {
        WorkflowAssignmentRuleDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowAssignmentRuleDTO> update(
            @PathVariable Integer id,
            @RequestBody WorkflowAssignmentRuleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
