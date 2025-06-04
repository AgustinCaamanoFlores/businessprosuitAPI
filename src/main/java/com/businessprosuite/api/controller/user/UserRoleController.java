package com.businessprosuite.api.controller.user;

import com.businessprosuite.api.dto.user.UserUserRoleDTO;
import com.businessprosuite.api.service.user.UserUserRoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {
    private final UserUserRoleService service;

    public UserRoleController(UserUserRoleService service) {
        this.service = service;
    }

    @GetMapping public ResponseEntity<List<UserUserRoleDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/{userId}/{roleId}")
    public ResponseEntity<UserUserRoleDTO> getById(
            @PathVariable Integer userId,
            @PathVariable Integer roleId) {
        return ResponseEntity.ok(service.findById(userId, roleId));
    }
    @PostMapping public ResponseEntity<UserUserRoleDTO> create(
            @Valid @RequestBody UserUserRoleDTO dto) {
        UserUserRoleDTO created = service.create(dto);
        String path = String.format("/api/user-roles/%d/%d",
                created.getUserId(),
                created.getRoleId());
        return ResponseEntity.created(URI.create(path)).body(created);
    }
    @DeleteMapping("/{userId}/{roleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer userId,
            @PathVariable Integer roleId) {
        service.delete(userId, roleId);
        return ResponseEntity.noContent().build();
    }
}
