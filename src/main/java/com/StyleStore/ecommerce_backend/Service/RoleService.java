package com.StyleStore.ecommerce_backend.Service;

import com.StyleStore.ecommerce_backend.Model.Permiso;
import com.StyleStore.ecommerce_backend.Model.Rol;
import com.StyleStore.ecommerce_backend.Model.RolPermiso;
import com.StyleStore.ecommerce_backend.Repository.PermisoRepository;
import com.StyleStore.ecommerce_backend.Repository.RolPermisoRepository;
import com.StyleStore.ecommerce_backend.Repository.RolRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RolPermisoRepository rolPermisoRepository;
    // Este método se ejecutará cuando la aplicación se inicie
    @PostConstruct
    public void initRolesAndPermisos() {
        // Crear roles por defecto si no existen
        Rol adminRol = createRoleIfNotFound(Rol.RolNombre.ROLE_ADMIN);
        Rol empleadoRol = createRoleIfNotFound(Rol.RolNombre.ROLE_EMPLEADO);
        Rol clienteRol = createRoleIfNotFound(Rol.RolNombre.ROLE_CLIENTE);

        // Crear permisos por defecto si no existen
        Permiso readPermiso = createPermisoIfNotFound("READ_PRIVILEGES");
        Permiso writePermiso = createPermisoIfNotFound("WRITE_PRIVILEGES");
        Permiso deletePermiso = createPermisoIfNotFound("DELETE_PRIVILEGES");

        // Asocia permisos a roles
        createRolPermisoIfNotFound(adminRol, readPermiso);
        createRolPermisoIfNotFound(adminRol, writePermiso);
        createRolPermisoIfNotFound(adminRol, deletePermiso);

        createRolPermisoIfNotFound(empleadoRol, readPermiso);
        createRolPermisoIfNotFound(clienteRol, readPermiso);
    }

    // Método para crear un rol si no existe
    private Rol createRoleIfNotFound(Rol.RolNombre roleName) {
        return rolRepository.findByNombre(roleName)
                .orElseGet(() -> {
                    Rol role = new Rol();
                    role.setNombre(roleName);
                    return rolRepository.save(role);
                });
    }

    private Permiso createPermisoIfNotFound(String permisoName) {
        return permisoRepository.findByNombre(permisoName)
                .orElseGet(() -> {
                    Permiso permiso = new Permiso();
                    permiso.setNombre(permisoName);
                    return permisoRepository.save(permiso);
                });
    }

    private void createRolPermisoIfNotFound(Rol rol, Permiso permiso) {
        if (!rolPermisoRepository.existsByRolAndPermiso(rol, permiso)) {
            RolPermiso rolPermiso = new RolPermiso();
            rolPermiso.setRol(rol);
            rolPermiso.setPermiso(permiso);
            rolPermisoRepository.save(rolPermiso);
        }
    }
}

