package com.StyleStore.ecommerce_backend.generateJWT;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        // Generar una clave segura de 512 bits para HS512
        byte[] key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512).getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(key);
        System.out.println("Clave JWT en Base64: " + base64Key);
    }
}
