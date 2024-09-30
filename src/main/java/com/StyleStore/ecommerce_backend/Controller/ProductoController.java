package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Dto.ProductoDto;
import com.StyleStore.ecommerce_backend.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping

    public ResponseEntity<List<ProductoDto>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/{id}")

    public ResponseEntity<ProductoDto> getProducto(@PathVariable UUID id) {
        return ResponseEntity.ok(productoService.getProducto(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoDto> createProducto(@RequestBody ProductoDto productoDto) {
        return ResponseEntity.ok(productoService.createProducto(productoDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoDto> updateProducto(@PathVariable UUID id, @RequestBody ProductoDto productoDto) {
        return ResponseEntity.ok(productoService.updateProducto(id, productoDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProducto(@PathVariable UUID id) {
        productoService.deleteProducto(id);
        return ResponseEntity.ok().build();
    }
}

