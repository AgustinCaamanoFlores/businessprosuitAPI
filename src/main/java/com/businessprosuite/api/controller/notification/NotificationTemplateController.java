package com.businessprosuite.api.controller.notification;

import com.businessprosuite.api.dto.notification.NotificationTemplateDTO;
import com.businessprosuite.api.service.notification.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification-templates")
@RequiredArgsConstructor
public class NotificationTemplateController {

    private final NotificationTemplateService service;

    @GetMapping
    public ResponseEntity<List<NotificationTemplateDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationTemplateDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<NotificationTemplateDTO> create(@RequestBody NotificationTemplateDTO dto) {
        NotificationTemplateDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationTemplateDTO> update(
            @PathVariable Integer id,
            @RequestBody NotificationTemplateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
