package com.StyleStore.ecommerce_backend.Service;


import com.StyleStore.ecommerce_backend.Dto.JwtResponse;
import com.StyleStore.ecommerce_backend.Dto.LoginRequest;
import com.StyleStore.ecommerce_backend.Dto.UsuarioDto;
import com.StyleStore.ecommerce_backend.Model.Rol;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.RolRepository;
import com.StyleStore.ecommerce_backend.Repository.UsuarioRepository;
import com.StyleStore.ecommerce_backend.Security.jwt.JwtUtils;
import com.StyleStore.ecommerce_backend.Security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    JwtUtils jwtUtils;

//    public JwtResponse authenticateUser(LoginRequest loginRequest) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String jwt = jwtUtils.generateJwtToken(authentication);
//
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//            String role = userDetails.getAuthorities().stream()
//                    .findFirst()
//                    .map(item -> item.getAuthority())
//                    .orElse("ROLE_USER");
//
//            return new JwtResponse(jwt,
//                    userDetails.getId(),
//                    userDetails.getUsername(),
//                    userDetails.getEmail(),
//                    role);
//        } catch (Exception e) {
//            throw new RuntimeException("Error durante la autenticación", e);
//        }
//    }


    // Método para registrar un cliente
    @Transactional
    public UsuarioDto registerCliente(UsuarioDto usuarioDto) {
        // Verificar si ya existe el email
        if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new RuntimeException("Error: El email ya está en uso!");
        }

        // Crear un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDto.getUsername());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));

        // Asignar el rol CLIENTE
        Rol rolCliente = rolRepository.findByNombre(Rol.RolNombre.ROLE_CLIENTE)
                .orElseThrow(() -> new RuntimeException("Error: Rol CLIENTE no encontrado."));
        usuario.setRol(rolCliente);

        // Guardar el usuario en la base de datos
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // Convertir a DTO para la respuesta
        // Usar UsuarioService para convertir a DTO
        return usuarioService.convertToDto(savedUsuario);
    }
public JwtResponse authenticateUser(LoginRequest loginRequest) {
    try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());  // Cambiar para manejar múltiples roles

        return new JwtResponse(jwt,
                userDetails.getUsername(),
                roles);  // Aquí usas el Set<String> para roles
    } catch (Exception e) {
        throw new RuntimeException("Error durante la autenticación", e);
    }
}

    public void logoutUser() {
        try {
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            throw new RuntimeException("Error durante el cierre de sesión", e);
        }
    }

    public Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                // Buscar el usuario en la base de datos por su nombre de usuario
                return usuarioRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            }
        }
        throw new RuntimeException("No se pudo obtener el usuario autenticado");
    }
}
