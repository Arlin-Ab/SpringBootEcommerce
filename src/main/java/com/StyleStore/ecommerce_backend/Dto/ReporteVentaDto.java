package com.StyleStore.ecommerce_backend.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ReporteVentaDto {

    private UUID id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private UUID sucursalId;
    private BigDecimal totalVentas;

    // Getters y Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public UUID getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(UUID sucursalId) {
        this.sucursalId = sucursalId;
    }

    public BigDecimal getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(BigDecimal totalVentas) {
        this.totalVentas = totalVentas;
    }
}

