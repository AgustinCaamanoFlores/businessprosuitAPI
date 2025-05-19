package com.businessprosuite.api.controller.hr;

import com.businessprosuite.api.dto.hr.LeaveRequestDTO;
import com.businessprosuite.api.service.hr.LeaveRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hr/leave-requests")
public class LeaveRequestController {

    private final LeaveRequestService service;

    public LeaveRequestController(LeaveRequestService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequestDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<LeaveRequestDTO> create(
            @Valid @RequestBody LeaveRequestDTO dto) {
        LeaveRequestDTO created = service.create(dto);
        URI location = URI.create("/api/hr/leave-requests/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody LeaveRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
