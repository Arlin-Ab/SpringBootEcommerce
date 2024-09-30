package com.StyleStore.ecommerce_backend.Model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ItemCarrito")
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "carrito_Id", nullable = false)
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "producto_Id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "pedidoid", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private Integer cantidad;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
