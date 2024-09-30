package com.StyleStore.ecommerce_backend.config.Initializer;

import com.StyleStore.ecommerce_backend.Model.Rol;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.RolRepository;
import com.StyleStore.ecommerce_backend.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        return args -> {
            // Crear rol ADMIN si no existe
            Optional<Rol> adminRole = rolRepository.findByNombre(Rol.RolNombre.ROLE_ADMIN);
            if (adminRole.isEmpty()) {
                Rol rolAdmin = new Rol();
                rolAdmin.setNombre(Rol.RolNombre.ROLE_ADMIN); // Usar RolNombre en lugar de un string
                rolRepository.save(rolAdmin);
                System.out.println("Rol ADMIN creado");
            } else {
                System.out.println("Rol ADMIN ya existe");
            }

            // Crear usuario admin si no existe
            if (!usuarioRepository.existsByEmail("admin@gmail.com")) {
                Usuario admin = new Usuario();
                admin.setEmail("admin@gmail.com");
                admin.setUsername("Arlin"); // Añadir nombre de usuario para admin
                admin.setPassword(passwordEncoder.encode("1234")); // Encriptar la contraseña

                // Obtener el rol ROLE_ADMIN
                adminRole = rolRepository.findByNombre(Rol.RolNombre.ROLE_ADMIN);
                if (adminRole.isPresent()) {
                    admin.setRol(adminRole.get()); // Asignar el rol al usuario
                    usuarioRepository.save(admin); // Guardar el usuario
                    System.out.println("Usuario admin creado");
                } else {
                    System.out.println("Error: Rol ADMIN no encontrado");
                }
            } else {
                System.out.println("Usuario admin ya existe");
            }
        };
    }

}

