package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Dto.ItemCarritoDto;
import com.StyleStore.ecommerce_backend.Service.ItemCarritoService;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/item-carrito")
public class ItemCarritoController {

    @Autowired
    private ItemCarritoService itemCarritoService;

    @GetMapping
    public ResponseEntity<List<ItemCarritoDto>> getAllItems() {
        try {
            List<ItemCarritoDto> items = itemCarritoService.getAllItems();
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCarritoDto> getItemById(@PathVariable UUID id) {
        try {
            ItemCarritoDto item = itemCarritoService.getItemById(id);
            return ResponseEntity.ok(item);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ItemCarritoDto> addItem(@RequestBody ItemCarritoDto itemCarritoDto) {
        try {
            ItemCarritoDto newItem = itemCarritoService.addItem(itemCarritoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCarritoDto> updateItem(@PathVariable UUID id, @RequestBody ItemCarritoDto itemCarritoDto) {
        try {
            ItemCarritoDto updatedItem = itemCarritoService.updateItem(id, itemCarritoDto);
            return ResponseEntity.ok(updatedItem);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID id) {
        try {
            itemCarritoService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


