package com.businessprosuite.api.controller.user;

import com.businessprosuite.api.dto.user.UserDTO;
import com.businessprosuite.api.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    /** Listado de todos los usuarios */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener un usuario por su ID */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear un nuevo usuario */
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO dto) {
        UserDTO created = service.create(dto);
        // Construimos la URI /api/users/{id}
        URI location = URI.create(String.format("/api/users/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar un usuario existente */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar un usuario */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
