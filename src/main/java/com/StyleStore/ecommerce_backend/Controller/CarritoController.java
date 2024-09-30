package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Dto.CarritoDto;
import com.StyleStore.ecommerce_backend.Service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CarritoDto>> getAllCarritos() {
        return ResponseEntity.ok(carritoService.getAllCarritos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CarritoDto> getCarrito(@PathVariable UUID id) {
        return ResponseEntity.ok(carritoService.getCarrito(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CarritoDto> createCarrito(@RequestBody CarritoDto carritoDto) {
        return ResponseEntity.ok(carritoService.createCarrito(carritoDto));
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CarritoDto> updateCarritoEstado(@PathVariable UUID id, @RequestBody String nuevoEstado) {
        return ResponseEntity.ok(carritoService.updateCarritoEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCarrito(@PathVariable UUID id) {
        carritoService.deleteCarrito(id);
        return ResponseEntity.ok().build();
    }
}

