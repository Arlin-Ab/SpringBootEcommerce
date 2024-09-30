package com.StyleStore.ecommerce_backend.Dto;

import java.util.UUID;

public class ItemCarritoDto {

    private UUID id;
    private UUID carritoId;
    private UUID productoId;
    private UUID pedidoId;
    private Integer cantidad;

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(UUID carritoId) {
        this.carritoId = carritoId;
    }

    public UUID getProductoId() {
        return productoId;
    }

    public void setProductoId(UUID productoId) {
        this.productoId = productoId;
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}

