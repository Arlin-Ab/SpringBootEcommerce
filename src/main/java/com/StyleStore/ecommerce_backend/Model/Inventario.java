package com.StyleStore.ecommerce_backend.Model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "productoId", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "sucursalId", nullable = false)
    private Sucursal sucursal;

    @Column
    private Integer cantidadDisponible;
    public Inventario(){

    }
    public Inventario(Producto producto, Sucursal sucursal, Integer cantidadDisponible) {
        this.producto = producto;
        this.sucursal = sucursal;
        this.cantidadDisponible = cantidadDisponible;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}
