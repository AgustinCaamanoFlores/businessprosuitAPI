package com.businessprosuite.api.controller.asset;

import com.businessprosuite.api.dto.asset.AssetDTO;
import com.businessprosuite.api.service.asset.AssetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService service;

    public AssetController(AssetService service) {
        this.service = service;
    }

    /** Listar todos los assets */
    @GetMapping
    public ResponseEntity<List<AssetDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener asset por ID */
    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear nuevo asset */
    @PostMapping
    public ResponseEntity<AssetDTO> create(@Valid @RequestBody AssetDTO dto) {
        AssetDTO created = service.create(dto);
        URI location = URI.create("/api/assets/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar asset existente */
    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AssetDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar asset por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
