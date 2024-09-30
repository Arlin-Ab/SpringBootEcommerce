package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Dto.InventarioDto;
import com.StyleStore.ecommerce_backend.Service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InventarioDto>> getAllInventarios() {
        return ResponseEntity.ok(inventarioService.getAllInventarios());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventarioDto> getInventario(@PathVariable UUID id) {
        return ResponseEntity.ok(inventarioService.getInventario(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventarioDto> createInventario(@RequestBody InventarioDto inventarioDto) {
        return ResponseEntity.ok(inventarioService.createInventario(inventarioDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventarioDto> updateInventario(@PathVariable UUID id, @RequestBody InventarioDto inventarioDto) {
        return ResponseEntity.ok(inventarioService.updateInventario(id, inventarioDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteInventario(@PathVariable UUID id) {
        inventarioService.deleteInventario(id);
        return ResponseEntity.ok().build();
    }
}

