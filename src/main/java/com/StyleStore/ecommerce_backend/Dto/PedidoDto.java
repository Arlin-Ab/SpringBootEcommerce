package com.StyleStore.ecommerce_backend.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PedidoDto {

    private UUID id;
    private UUID usuarioId;
    private LocalDateTime fecha;
    private BigDecimal montoTotal;
    private String estado;
    private String direccionEnvio;
    private UUID metodoDePagoId;

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public UUID getMetodoDePagoId() {
        return metodoDePagoId;
    }

    public void setMetodoDePagoId(UUID metodoDePagoId) {
        this.metodoDePagoId = metodoDePagoId;
    }
}

