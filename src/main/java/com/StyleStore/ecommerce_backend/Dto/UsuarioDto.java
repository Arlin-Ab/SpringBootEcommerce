package com.StyleStore.ecommerce_backend.Dto;

import com.StyleStore.ecommerce_backend.Model.Rol;

import java.util.UUID;

public class UsuarioDto {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private Rol.RolNombre rol;

    // Constructors
    public UsuarioDto() {}

    public UsuarioDto(UUID id, String username, String email, Rol.RolNombre rol) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.rol = rol;
    }
    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol.RolNombre getRol() {
        return rol;
    }

    public void setRol(Rol.RolNombre rol) {
        this.rol = rol;
    }
}
