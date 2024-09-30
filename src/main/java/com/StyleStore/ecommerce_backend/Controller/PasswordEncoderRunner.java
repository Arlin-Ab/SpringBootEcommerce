package com.StyleStore.ecommerce_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderRunner implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        encodePassword();
    }

    public void encodePassword() {
        String password = "1234"; // Aquí puedes cambiar la contraseña que quieres codificar
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println("Contraseña codificada: " + encodedPassword);  // Imprimir en consola
    }
}

