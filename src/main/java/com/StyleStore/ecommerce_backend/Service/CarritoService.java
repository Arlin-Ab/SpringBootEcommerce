package com.StyleStore.ecommerce_backend.Service;


import com.StyleStore.ecommerce_backend.Dto.CarritoDto;
import com.StyleStore.ecommerce_backend.Model.Carrito;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.CarritoRepository;
import com.StyleStore.ecommerce_backend.Repository.UsuarioRepository;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private AuthService authService;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final List<String> ESTADOS_PERMITIDOS = Arrays.asList("Activo", "Comprado", "Cancelado");

    @Transactional(readOnly = true)
    public List<CarritoDto> getAllCarritos() {
        try {
            return carritoRepository.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los carritos", e);
        }
    }

    @Transactional(readOnly = true)
    public CarritoDto getCarrito(UUID id) {
        try {
            return carritoRepository.findById(id)
                    .map(this::convertToDto)
                    .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado con id: " + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el carrito con id: " + id, e);
        }
    }

    @Transactional
    public CarritoDto createCarrito(CarritoDto carritoDto) {
        try {
            Usuario usuario = usuarioRepository.findById(carritoDto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + carritoDto.getUsuarioId()));

            Carrito carrito = new Carrito();
            carrito.setUsuario(usuario);
            carrito.setFechaCreacion(LocalDateTime.now());
            carrito.setEstado("Activo");

            Carrito savedCarrito = carritoRepository.save(carrito);
            // Registro en bitácora
            bitacoraService.registrarActividad(usuario, "Creación de carrito con ID: " + savedCarrito.getId());

            return convertToDto(savedCarrito);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el carrito", e);
        }
    }


    @Transactional
    public CarritoDto updateCarritoEstado(UUID id, String nuevoEstado) {
        try {
            // Validar si el estado es válido
            if (!ESTADOS_PERMITIDOS.contains(nuevoEstado)) {
                throw new IllegalArgumentException("Estado no válido. Estados permitidos: " + ESTADOS_PERMITIDOS);
            }

            // Buscar el carrito por ID
            Carrito carrito = carritoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado con id: " + id));

            // Actualizar el estado del carrito
            carrito.setEstado(nuevoEstado);

            // Guardar los cambios
            Carrito updatedCarrito = carritoRepository.save(carrito);

            // Registro en bitácora
            Usuario usuario = carrito.getUsuario();  // Obtener el usuario asociado al carrito
            bitacoraService.registrarActividad(usuario, "Actualización de estado de carrito con ID: " + updatedCarrito.getId() + " a " + nuevoEstado);

            return convertToDto(updatedCarrito);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el estado del carrito", e);
        }
    }

    @Transactional
    public void deleteCarrito(UUID id) {
        try {

            Carrito carrito = carritoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado con id: " + id));

            carritoRepository.deleteById(id);

            // Registro en bitácora
            Usuario usuario = carrito.getUsuario();  // Obtener el usuario asociado al carrito
            bitacoraService.registrarActividad(usuario, "Eliminación de carrito con ID: " + id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el carrito con id: " + id, e);
        }
    }

    private CarritoDto convertToDto(Carrito carrito) {
        CarritoDto dto = new CarritoDto();
        dto.setId(carrito.getId());
        dto.setUsuarioId(carrito.getUsuario().getId());
        dto.setFechaCreacion(carrito.getFechaCreacion());
        dto.setEstado(carrito.getEstado());
        return dto;
    }
}

