package com.StyleStore.ecommerce_backend.Service;

import com.StyleStore.ecommerce_backend.Dto.InventarioDto;
import com.StyleStore.ecommerce_backend.Model.Inventario;
import com.StyleStore.ecommerce_backend.Model.Producto;
import com.StyleStore.ecommerce_backend.Model.Sucursal;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.InventarioRepository;
import com.StyleStore.ecommerce_backend.Repository.ProductoRepository;
import com.StyleStore.ecommerce_backend.Repository.SucursalRepository;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InventarioService {
    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private AuthService authService;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Transactional(readOnly = true)
    public List<InventarioDto> getAllInventarios() {
        try {
            return inventarioRepository.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los inventarios", e);
        }
    }

    @Transactional(readOnly = true)
    public InventarioDto getInventario(UUID id) {
        try {
            return inventarioRepository.findById(id)
                    .map(this::convertToDto)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado con id: " + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el inventario con id: " + id, e);
        }
    }

    @Transactional
    public InventarioDto createInventario(InventarioDto inventarioDto) {
        try {
            Inventario inventario = convertToEntity(inventarioDto);
            Inventario savedInventario = inventarioRepository.save(inventario);
            // Registrar en bitácora
            Usuario usuario = authService.obtenerUsuarioAutenticado(); // Obtener el usuario autenticado
            bitacoraService.registrarActividad(usuario, "Creación de inventario para el producto: " + inventario.getProducto().getNombre());

            return convertToDto(savedInventario);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el inventario", e);
        }
    }

    @Transactional
    public InventarioDto updateInventario(UUID id, InventarioDto inventarioDto) {
        try {
            Inventario inventario = inventarioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado con id: " + id));

            inventario.setCantidadDisponible(inventarioDto.getCantidadDisponible());

            Inventario updatedInventario = inventarioRepository.save(inventario);
            // Registrar en bitácora
            Usuario usuario = authService.obtenerUsuarioAutenticado(); // Obtener el usuario autenticado
            bitacoraService.registrarActividad(usuario, "Actualización de inventario para el producto: " + inventario.getProducto().getNombre());

            return convertToDto(updatedInventario);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el inventario con id: " + id, e);
        }
    }

    @Transactional
    public void deleteInventario(UUID id) {
        try {
            Inventario inventario = inventarioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado con id: " + id));

            inventarioRepository.deleteById(id);

            // Registrar en bitácora
            Usuario usuario = authService.obtenerUsuarioAutenticado(); // Obtener el usuario autenticado
            bitacoraService.registrarActividad(usuario, "Eliminación de inventario para el producto: " + inventario.getProducto().getNombre());
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el inventario con id: " + id, e);
        }
    }

    private InventarioDto convertToDto(Inventario inventario) {
        InventarioDto dto = new InventarioDto();
        dto.setId(inventario.getId());
        dto.setProductoId(inventario.getProducto().getId());
        dto.setSucursalId(inventario.getSucursal().getId());
        dto.setCantidadDisponible(inventario.getCantidadDisponible());
        return dto;
    }

    private Inventario convertToEntity(InventarioDto dto) {
        Inventario inventario = new Inventario();

        // Buscar el producto por su ID
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + dto.getProductoId()));
        inventario.setProducto(producto);

        // Buscar la sucursal por su ID
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursalId())
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id: " + dto.getSucursalId()));
        inventario.setSucursal(sucursal);

        // Asignar la cantidad disponible
        inventario.setCantidadDisponible(dto.getCantidadDisponible());

        return inventario;
    }
}

