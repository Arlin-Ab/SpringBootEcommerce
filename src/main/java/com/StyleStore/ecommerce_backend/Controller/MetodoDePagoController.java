package com.StyleStore.ecommerce_backend.Controller;


import com.StyleStore.ecommerce_backend.Dto.MetodoDePagoDto;
import com.StyleStore.ecommerce_backend.Service.MetodoDePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/metodos-de-pago")
public class MetodoDePagoController {

    @Autowired
    private MetodoDePagoService metodoDePagoService;

    @GetMapping
    public ResponseEntity<List<MetodoDePagoDto>> getAllMetodosDePago() {
        return ResponseEntity.ok(metodoDePagoService.getAllMetodosDePago());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoDePagoDto> getMetodoDePago(@PathVariable UUID id) {
        return ResponseEntity.ok(metodoDePagoService.getMetodoDePago(id));
    }

    @PostMapping
    public ResponseEntity<MetodoDePagoDto> createMetodoDePago(@RequestBody MetodoDePagoDto metodoDePagoDto) {
        return ResponseEntity.ok(metodoDePagoService.createMetodoDePago(metodoDePagoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoDePagoDto> updateMetodoDePago(@PathVariable UUID id, @RequestBody MetodoDePagoDto metodoDePagoDto) {
        return ResponseEntity.ok(metodoDePagoService.updateMetodoDePago(id, metodoDePagoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetodoDePago(@PathVariable UUID id) {
        metodoDePagoService.deleteMetodoDePago(id);
        return ResponseEntity.noContent().build();
    }
}

