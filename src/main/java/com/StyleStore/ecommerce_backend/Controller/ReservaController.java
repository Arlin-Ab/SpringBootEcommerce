package com.StyleStore.ecommerce_backend.Controller;


import com.StyleStore.ecommerce_backend.Dto.ReservaDto;
import com.StyleStore.ecommerce_backend.Service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservaDto>> getAllReservas() {
        return ResponseEntity.ok(reservaService.getAllReservas());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservaDto> getReserva(@PathVariable UUID id) {
        return ResponseEntity.ok(reservaService.getReserva(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReservaDto> createReserva(@RequestBody ReservaDto reservaDto) {
        return ResponseEntity.ok(reservaService.createReserva(reservaDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReservaDto> updateReserva(@PathVariable UUID id, @RequestBody ReservaDto reservaDto) {
        return ResponseEntity.ok(reservaService.updateReserva(id, reservaDto));
    }

}

