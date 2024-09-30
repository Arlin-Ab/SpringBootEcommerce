package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Dto.CreateSubcategoriaDto;
import com.StyleStore.ecommerce_backend.Dto.SubcategoriaDto;
import com.StyleStore.ecommerce_backend.Service.SubcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subcategorias")
public class SubcategoriaController {

    @Autowired
    private SubcategoriaService subcategoriaService;

    // Obtener todas las subcategorías
    @GetMapping
    public ResponseEntity<List<SubcategoriaDto>> getAllSubcategorias() {
        try {
            return ResponseEntity.ok(subcategoriaService.getAllSubcategorias());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Crear una nueva subcategoría
    @PostMapping
    public ResponseEntity<SubcategoriaDto> createSubcategoria(@RequestBody CreateSubcategoriaDto dto) {
        try {
            return ResponseEntity.ok(subcategoriaService.createSubcategoria(dto));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Actualizar una subcategoría
    @PutMapping("/{id}")
    public ResponseEntity<SubcategoriaDto> updateSubcategoria(@PathVariable UUID id, @RequestBody CreateSubcategoriaDto dto) {
        try {
            return ResponseEntity.ok(subcategoriaService.updateSubcategoria(id, dto));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Eliminar una subcategoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubcategoria(@PathVariable UUID id) {
        try {
            subcategoriaService.deleteSubcategoria(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

