package com.StyleStore.ecommerce_backend.Repository;

import com.StyleStore.ecommerce_backend.Model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RolRepository extends JpaRepository<Rol, UUID> {
    Optional<Rol> findByNombre(Rol.RolNombre nombre);
}
