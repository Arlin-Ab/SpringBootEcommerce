package com.StyleStore.ecommerce_backend.Service;

import com.StyleStore.ecommerce_backend.Dto.SucursalDto;
import com.StyleStore.ecommerce_backend.Model.Sucursal;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.SucursalRepository;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SucursalService {

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private AuthService authService;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Transactional(readOnly = true)
    public List<SucursalDto> getAllSucursales() {
        try {
            return sucursalRepository.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las sucursales", e);
        }
    }

    @Transactional(readOnly = true)
    public SucursalDto getSucursalById(UUID id) {
        try {
            return sucursalRepository.findById(id)
                    .map(this::convertToDto)
                    .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id: " + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la sucursal con id: " + id, e);
        }
    }

    @Transactional
    public SucursalDto createSucursal(SucursalDto sucursalDto) {
        try {
            Sucursal sucursal = convertToEntity(sucursalDto);
            Sucursal savedSucursal = sucursalRepository.save(sucursal);
            // Registrar en bitácora
            Usuario usuario = authService.obtenerUsuarioAutenticado(); // Obtener el usuario autenticado
            bitacoraService.registrarActividad(usuario, "Creación de sucursal: " + sucursal.getNombre());

            return convertToDto(savedSucursal);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la sucursal", e);
        }
    }

    @Transactional
    public SucursalDto updateSucursal(UUID id, SucursalDto sucursalDto) {
        try {
            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id: " + id));

            sucursal.setNombre(sucursalDto.getNombre());
            sucursal.setDireccion(sucursalDto.getDireccion());
            sucursal.setTelefono(sucursalDto.getTelefono());


            Sucursal updatedSucursal = sucursalRepository.save(sucursal);
            // Registrar en bitácora
            Usuario usuario = authService.obtenerUsuarioAutenticado(); // Obtener el usuario autenticado
            bitacoraService.registrarActividad(usuario, "Actualización de sucursal: " + sucursal.getNombre());

            return convertToDto(updatedSucursal);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la sucursal con id: " + id, e);
        }
    }

    @Transactional
    public void deleteSucursal(UUID id) {
        try {

            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id: " + id));

            sucursalRepository.deleteById(id);

            // Registrar en bitácora
            Usuario usuario = authService.obtenerUsuarioAutenticado(); // Obtener el usuario autenticado
            bitacoraService.registrarActividad(usuario, "Eliminación de sucursal: " + sucursal.getNombre());
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la sucursal con id: " + id, e);
        }
    }

    // Métodos de conversión
    private SucursalDto convertToDto(Sucursal sucursal) {
        SucursalDto dto = new SucursalDto();
        dto.setId(sucursal.getId());
        dto.setNombre(sucursal.getNombre());
        dto.setDireccion(sucursal.getDireccion());
        dto.setTelefono(sucursal.getTelefono());
        return dto;
    }

    private Sucursal convertToEntity(SucursalDto dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setTelefono(dto.getTelefono());
        return sucursal;
    }
}

