package com.StyleStore.ecommerce_backend.Service;


import com.StyleStore.ecommerce_backend.Dto.ReservaDto;
import com.StyleStore.ecommerce_backend.Model.Producto;
import com.StyleStore.ecommerce_backend.Model.Reserva;
import com.StyleStore.ecommerce_backend.Repository.ProductoRepository;
import com.StyleStore.ecommerce_backend.Repository.ReservaRepository;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ProductoRepository productoRepository;  // Necesario para manejar el stock

    @Transactional(readOnly = true)
    public List<ReservaDto> getAllReservas() {
        try {
            return reservaRepository.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las reservas", e);
        }
    }

    @Transactional(readOnly = true)
    public ReservaDto getReserva(UUID id) {
        try {
            return reservaRepository.findById(id)
                    .map(this::convertToDto)
                    .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la reserva con id: " + id, e);
        }
    }

    @Transactional
    public ReservaDto createReserva(ReservaDto reservaDto) {
        try {
            Reserva reserva = convertToEntity(reservaDto);
            reserva.setEstado("Pendiente"); // Establecer el estado inicial como "Pendiente"
            Reserva savedReserva = reservaRepository.save(reserva);
            return convertToDto(savedReserva);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la reserva", e);
        }
    }

    @Transactional
    public ReservaDto updateReserva(UUID id, ReservaDto reservaDto) {
        try {
            Reserva reserva = reservaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));

            reserva.setEstado(reservaDto.getEstado()); // Actualización del estado
            reserva.setFechaReserva(reservaDto.getFechaReserva());

            Reserva updatedReserva = reservaRepository.save(reserva);
            return convertToDto(updatedReserva);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la reserva con id: " + id, e);
        }
    }

    @Transactional
    public void deleteReserva(UUID id) {
        try {
            if (!reservaRepository.existsById(id)) {
                throw new ResourceNotFoundException("Reserva no encontrada con id: " + id);
            }
            reservaRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la reserva con id: " + id, e);
        }
    }

    @Transactional
    public ReservaDto updateEstadoReserva(UUID id, String nuevoEstado) {
        try {
            Reserva reserva = reservaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));

            // Validar que el estado sea válido
            if (!List.of("Pendiente", "Confirmada", "Cancelada").contains(nuevoEstado)) {
                throw new IllegalArgumentException("Estado no válido");
            }

            reserva.setEstado(nuevoEstado);
            Reserva updatedReserva = reservaRepository.save(reserva);

            // Si el estado cambia a "Cancelada", devolver el stock del producto
            if ("Cancelada".equals(nuevoEstado)) {
                Producto producto = reserva.getProducto();
                producto.setStock(producto.getStock() + 1);
                productoRepository.save(producto);
            }

            return convertToDto(updatedReserva);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el estado de la reserva", e);
        }
    }

    private ReservaDto convertToDto(Reserva reserva) {
        ReservaDto dto = new ReservaDto();
        dto.setId(reserva.getId());
        dto.setEstado(reserva.getEstado());
        dto.setFechaReserva(reserva.getFechaReserva());
        dto.setProductoId(reserva.getProducto().getId());
        dto.setUsuarioId(reserva.getUsuario().getId());
        return dto;
    }

    private Reserva convertToEntity(ReservaDto dto) {
        Reserva reserva = new Reserva();
        reserva.setEstado(dto.getEstado());
        reserva.setFechaReserva(dto.getFechaReserva());
        // Se asigna manualmente Producto y Usuario al crear la reserva
        return reserva;
    }
}

