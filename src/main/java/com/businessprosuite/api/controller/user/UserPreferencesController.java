package com.businessprosuite.api.controller.user;

import com.businessprosuite.api.dto.user.UserPreferencesDTO;
import com.businessprosuite.api.service.user.UserPreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class UserPreferencesController {

    private final UserPreferencesService prefsService;

    @GetMapping
    public ResponseEntity<List<UserPreferencesDTO>> getAll() {
        return ResponseEntity.ok(prefsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPreferencesDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(prefsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserPreferencesDTO> create(
            @RequestBody UserPreferencesDTO dto) {
        UserPreferencesDTO created = prefsService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPreferencesDTO> update(
            @PathVariable Integer id,
            @RequestBody UserPreferencesDTO dto) {
        return ResponseEntity.ok(prefsService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        prefsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
