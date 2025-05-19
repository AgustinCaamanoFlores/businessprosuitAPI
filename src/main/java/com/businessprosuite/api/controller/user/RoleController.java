package com.businessprosuite.api.controller.user;

import com.businessprosuite.api.dto.user.UserRoleDTO;
import com.businessprosuite.api.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity<List<UserRoleDTO>> getAll() {
        return ResponseEntity.ok(userRoleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userRoleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserRoleDTO> create(@RequestBody UserRoleDTO dto) {
        UserRoleDTO created = userRoleService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRoleDTO> update(
            @PathVariable Integer id,
            @RequestBody UserRoleDTO dto) {
        return ResponseEntity.ok(userRoleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userRoleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
