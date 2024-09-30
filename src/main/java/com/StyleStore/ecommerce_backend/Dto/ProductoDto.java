package com.StyleStore.ecommerce_backend.Dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductoDto {

        private UUID id;

        @NotNull(message = "El nombre no puede ser nulo")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        private String nombre;

        @NotNull(message = "El precio no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        private BigDecimal precio;

        @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
        private String descripcion;

        @Size(max = 50, message = "La talla no puede tener más de 50 caracteres")
        private String talla;

        @Size(max = 50, message = "El color no puede tener más de 50 caracteres")
        private String color;

        @Size(max = 100, message = "La marca no puede tener más de 100 caracteres")
        private String marca;

        @NotNull(message = "El stock no puede ser nulo")
        private Integer stock;

        @Size(max = 255, message = "La URL de la imagen no puede tener más de 255 caracteres")
        private String imagenUrl;

       @NotNull(message = "La subcategoría no puede ser nula")
       private UUID subcategoriaId;


    // Getters and Setters
        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public BigDecimal getPrecio() {
            return precio;
        }

        public void setPrecio(BigDecimal precio) {
            this.precio = precio;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getTalla() {
            return talla;
        }

        public void setTalla(String talla) {
            this.talla = talla;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getMarca() {
            return marca;
        }

        public void setMarca(String marca) {
            this.marca = marca;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }

        public String getImagenUrl() {
            return imagenUrl;
        }

        public void setImagenUrl(String imagenUrl) {
            this.imagenUrl = imagenUrl;
        }

        public UUID getSubcategoriaId() {
            return subcategoriaId; }
        public void setSubcategoriaId(UUID subcategoriaId) {
            this.subcategoriaId = subcategoriaId; }


}
