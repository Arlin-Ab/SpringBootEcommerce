package com.StyleStore.ecommerce_backend.Dto;
import java.util.Set;
import java.util.UUID;

public class JwtResponse {
//    private String token;
//    private String type = "Bearer";
//    private UUID id;
//    private String username;
//    private String email;
//    private String role;  // Cambiado a un solo String en lugar de Set<String>
//    private String token;
//    private String type = "Bearer";
//    private String username;
//    private Set<String> roles;
    // Constructor
//    public JwtResponse(String token, UUID id, String username, String email, String role) {
//        this.token = token;
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.role = role;
//    }
private String token;
    private String type = "Bearer";
    private String username;
    private Set<String> roles;

    // Constructor
    public JwtResponse(String token, String username, Set<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
}

