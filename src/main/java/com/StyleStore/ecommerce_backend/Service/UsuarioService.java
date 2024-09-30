package com.StyleStore.ecommerce_backend.Service;

import com.StyleStore.ecommerce_backend.Dto.UsuarioDto;
import com.StyleStore.ecommerce_backend.Model.Rol;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.RolRepository;
import com.StyleStore.ecommerce_backend.Repository.UsuarioRepository;

import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private BitacoraService bitacoraService;


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsuarioDto> getAllUsuarios() {
        try {
            return usuarioRepository.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los usuarios", e);
        }
    }

    @Transactional(readOnly = true)
    public UsuarioDto getUsuario(UUID id) {
        try {
            return usuarioRepository.findById(id)
                    .map(this::convertToDto)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el usuario con id: " + id, e);
        }
    }

    @Transactional
    public UsuarioDto createUsuario(UsuarioDto usuarioDto) {
        try {
            if (usuarioRepository.existsByUsername(usuarioDto.getUsername())) {
                throw new RuntimeException("Error: El nombre de usuario ya está en uso!");
            }
            if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
                throw new RuntimeException("Error: El email ya está en uso!");
            }

            // Crear un nuevo usuario sin asignar manualmente el id
            Usuario usuario = new Usuario();
            usuario.setUsername(usuarioDto.getUsername());
            usuario.setEmail(usuarioDto.getEmail());
            usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));

            // Asignar el rol
            Rol.RolNombre rolNombre = usuarioDto.getRol();
            Rol rol = rolRepository.findByNombre(rolNombre)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            usuario.setRol(rol);

            // Guardar el usuario en la base de datos (el id será generado automáticamente)
            Usuario savedUsuario = usuarioRepository.save(usuario);

            // Registrar en Bitácora
            bitacoraService.registrarActividad(savedUsuario, "Creación de usuario: " + usuario.getUsername());
            return convertToDto(savedUsuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el usuario", e);
        }
    }

    @Transactional
    public UsuarioDto updateUsuario(UUID id, UsuarioDto usuarioDto) {
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

            usuario.setUsername(usuarioDto.getUsername());
            usuario.setEmail(usuarioDto.getEmail());

            if (usuarioDto.getPassword() != null && !usuarioDto.getPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
            }

            Rol.RolNombre rolNombre = usuarioDto.getRol();
            Rol rol = rolRepository.findByNombre(rolNombre)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            usuario.setRol(rol);

            Usuario updatedUsuario = usuarioRepository.save(usuario);
            // Registrar en Bitácora
            bitacoraService.registrarActividad(usuario, "Actualización de usuario: " + usuario.getUsername());
            return convertToDto(updatedUsuario);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error: Rol no válido.", e);  // Para el caso de un rol que no existe en el enum
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario con id: " + id, e);
        }
    }

    @Transactional
    public void deleteUsuario(UUID id) {
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));


            bitacoraService.registrarActividad(usuario, "Eliminación de usuario: " + usuario.getUsername());


            usuarioRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el usuario con id: " + id, e);
        }
    }

    public UsuarioDto convertToDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol().getNombre());
        return dto;
    }
}
