package com.StyleStore.ecommerce_backend.Repository;

import com.StyleStore.ecommerce_backend.Model.Permiso;
import com.StyleStore.ecommerce_backend.Model.Rol;
import com.StyleStore.ecommerce_backend.Model.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RolPermisoRepository extends JpaRepository<RolPermiso, UUID> {
    boolean existsByRolAndPermiso(Rol rol, Permiso permiso);
}
