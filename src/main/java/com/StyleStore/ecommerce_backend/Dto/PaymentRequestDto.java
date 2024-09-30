package com.StyleStore.ecommerce_backend.Dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentRequestDto {

    private BigDecimal monto;
    private String currency;
    private UUID pedidoId;

    // Getters y Setters

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        this.pedidoId = pedidoId;
    }
}

