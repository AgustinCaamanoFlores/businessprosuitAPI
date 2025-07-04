package com.businessprosuite.api.controller.notification;

import com.businessprosuite.api.dto.notification.NotificationQueueDTO;
import com.businessprosuite.api.service.notification.NotificationQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification-queue")
@RequiredArgsConstructor
public class NotificationQueueController {

    private final NotificationQueueService service;

    @GetMapping
    public ResponseEntity<List<NotificationQueueDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationQueueDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<NotificationQueueDTO> create(@RequestBody NotificationQueueDTO dto) {
        NotificationQueueDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationQueueDTO> update(
            @PathVariable Integer id,
            @RequestBody NotificationQueueDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/dispatch")
    public ResponseEntity<Void> dispatch() {
        service.dispatchPending();
        return ResponseEntity.accepted().build();
    }
}
