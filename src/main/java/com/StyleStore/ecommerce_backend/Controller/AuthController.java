package com.StyleStore.ecommerce_backend.Controller;


import com.StyleStore.ecommerce_backend.Dto.JwtResponse;
import com.StyleStore.ecommerce_backend.Dto.LoginRequest;
import com.StyleStore.ecommerce_backend.Dto.UsuarioDto;
import com.StyleStore.ecommerce_backend.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        authService.logoutUser();
        return ResponseEntity.ok("Logout successful");
    }
    // Nuevo método para registrar un cliente
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioDto usuarioDto) {
        UsuarioDto newUser = authService.registerCliente(usuarioDto);
        return ResponseEntity.ok(newUser);
    }
}
