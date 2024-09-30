package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Dto.BitacoraDto;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.UsuarioRepository;
import com.StyleStore.ecommerce_backend.Service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bitacoras")
public class BitacoraController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BitacoraService bitacoraService;

    // Registrar actividad en la bitácora
    @PostMapping("/registrar")
    public ResponseEntity<Void> registrarActividad(@RequestParam UUID usuarioId, @RequestParam String actividad) {
        // Asumimos que existe un método para obtener el usuario desde el ID
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        bitacoraService.registrarActividad(usuario, actividad);
        return ResponseEntity.ok().build();
    }

    // Obtener todas las bitácoras
    @GetMapping
    public ResponseEntity<List<BitacoraDto>> getAllBitacoras() {
        return ResponseEntity.ok(bitacoraService.getAllBitacoras());
    }
}

