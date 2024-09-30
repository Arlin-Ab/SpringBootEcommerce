package com.StyleStore.ecommerce_backend.Controller;


import com.StyleStore.ecommerce_backend.Dto.PedidoDto;
import com.StyleStore.ecommerce_backend.Service.PedidoService;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDto>> getAllPedidos() {
        try {
            List<PedidoDto> pedidos = pedidoService.getAllPedidos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getPedido(@PathVariable UUID id) {
        try {
            PedidoDto pedido = pedidoService.getPedido(id);
            return ResponseEntity.ok(pedido);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<PedidoDto> createPedido(@RequestBody PedidoDto pedidoDto) {
        try {
            PedidoDto createdPedido = pedidoService.createPedido(pedidoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> updatePedido(@PathVariable UUID id, @RequestBody PedidoDto pedidoDto) {
        try {
            PedidoDto updatedPedido = pedidoService.updatePedido(id, pedidoDto);
            return ResponseEntity.ok(updatedPedido);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/confirmar/{id}")
    public ResponseEntity<PedidoDto> confirmarPedido(@PathVariable UUID id) {
        try {
            PedidoDto confirmadoPedido = pedidoService.confirmarPedido(id);
            return ResponseEntity.ok(confirmadoPedido);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable UUID id) {
        try {
            pedidoService.deletePedido(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

