package com.businessprosuite.api.controller.user;

import com.businessprosuite.api.dto.user.UserUserRoleDTO;
import com.businessprosuite.api.service.user.UserUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-user-roles")
@RequiredArgsConstructor
public class UserUserRoleController {

    private final UserUserRoleService userRoleService;

    @GetMapping
    public ResponseEntity<List<UserUserRoleDTO>> getAll() {
        return ResponseEntity.ok(userRoleService.findAll());
    }

    @GetMapping("/{userId}/{roleId}")
    public ResponseEntity<UserUserRoleDTO> getById(
            @PathVariable Integer userId,
            @PathVariable Integer roleId) {
        return ResponseEntity.ok(userRoleService.findById(userId, roleId));
    }

    @PostMapping
    public ResponseEntity<UserUserRoleDTO> create(@RequestBody UserUserRoleDTO dto) {
        UserUserRoleDTO created = userRoleService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{userId}/{roleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer userId,
            @PathVariable Integer roleId) {
        userRoleService.delete(userId, roleId);
        return ResponseEntity.noContent().build();
    }
}
