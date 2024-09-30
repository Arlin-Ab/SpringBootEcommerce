package com.StyleStore.ecommerce_backend.Repository;

import com.StyleStore.ecommerce_backend.Model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, UUID> {
    List<Carrito> findByUsuarioId(UUID usuarioId);
}
