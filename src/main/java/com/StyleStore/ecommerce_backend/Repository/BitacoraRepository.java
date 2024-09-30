package com.StyleStore.ecommerce_backend.Repository;

import com.StyleStore.ecommerce_backend.Model.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BitacoraRepository extends JpaRepository<Bitacora, UUID> {
    List<Bitacora> findByUsuarioId(UUID usuarioId);
}

