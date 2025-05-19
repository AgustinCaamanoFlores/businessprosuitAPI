package com.businessprosuite.api.controller.asset;

import com.businessprosuite.api.dto.asset.AssetCategoryDTO;
import com.businessprosuite.api.service.asset.AssetCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/asset/categories")
public class AssetCategoryController {

    private final AssetCategoryService service;

    public AssetCategoryController(AssetCategoryService service) {
        this.service = service;
    }

    /** Listar todas las categorías de activo */
    @GetMapping
    public ResponseEntity<List<AssetCategoryDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /** Obtener categoría por ID */
    @GetMapping("/{id}")
    public ResponseEntity<AssetCategoryDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Crear nueva categoría */
    @PostMapping
    public ResponseEntity<AssetCategoryDTO> create(
            @Valid @RequestBody AssetCategoryDTO dto) {
        AssetCategoryDTO created = service.create(dto);
        URI location = URI.create("/api/asset/categories/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    /** Actualizar categoría existente */
    @PutMapping("/{id}")
    public ResponseEntity<AssetCategoryDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AssetCategoryDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /** Eliminar categoría por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
