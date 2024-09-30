package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Dto.CategoriaDto;
import com.StyleStore.ecommerce_backend.Service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.getAllCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> getCategoria(@PathVariable UUID id) {
        return ResponseEntity.ok(categoriaService.getCategoria(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> createCategoria(@RequestBody CategoriaDto categoriaDto) {
        return ResponseEntity.ok(categoriaService.createCategoria(categoriaDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> updateCategoria(@PathVariable UUID id, @RequestBody CategoriaDto categoriaDto) {
        return ResponseEntity.ok(categoriaService.updateCategoria(id, categoriaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable UUID id) {
        categoriaService.deleteCategoria(id);
        return ResponseEntity.ok().build();
    }
}

