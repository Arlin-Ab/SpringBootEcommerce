package com.StyleStore.ecommerce_backend.Dto;

import java.util.UUID;

public class InventarioDto {
    private UUID id;
    private UUID productoId;
    private UUID sucursalId;
    private Integer cantidadDisponible;

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductoId() {
        return productoId;
    }

    public void setProductoId(UUID productoId) {
        this.productoId = productoId;
    }

    public UUID getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(UUID sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}

