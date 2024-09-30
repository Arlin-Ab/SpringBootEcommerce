package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Dto.SucursalDto;
import com.StyleStore.ecommerce_backend.Service.SucursalService;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalDto>> getAllSucursales() {
        try {
            List<SucursalDto> sucursales = sucursalService.getAllSucursales();
            return ResponseEntity.ok(sucursales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalDto> getSucursalById(@PathVariable UUID id) {
        try {
            SucursalDto sucursal = sucursalService.getSucursalById(id);
            return ResponseEntity.ok(sucursal);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<SucursalDto> createSucursal(@RequestBody SucursalDto sucursalDto) {
        try {
            SucursalDto createdSucursal = sucursalService.createSucursal(sucursalDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSucursal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDto> updateSucursal(@PathVariable UUID id, @RequestBody SucursalDto sucursalDto) {
        try {
            SucursalDto updatedSucursal = sucursalService.updateSucursal(id, sucursalDto);
            return ResponseEntity.ok(updatedSucursal);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(@PathVariable UUID id) {
        try {
            sucursalService.deleteSucursal(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

