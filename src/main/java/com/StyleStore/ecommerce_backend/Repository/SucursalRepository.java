package com.StyleStore.ecommerce_backend.Repository;

import com.StyleStore.ecommerce_backend.Model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, UUID> {
}
