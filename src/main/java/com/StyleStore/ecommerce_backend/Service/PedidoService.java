package com.StyleStore.ecommerce_backend.Service;


import com.StyleStore.ecommerce_backend.Dto.PedidoDto;
import com.StyleStore.ecommerce_backend.Model.MetodoDePago;
import com.StyleStore.ecommerce_backend.Model.Pedido;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.MetodoDePagoRepository;
import com.StyleStore.ecommerce_backend.Repository.PedidoRepository;
import com.StyleStore.ecommerce_backend.Repository.UsuarioRepository;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private AuthService authService;

    @Autowired
    private MetodoDePagoRepository metodoDePagoRepository;

    @Transactional(readOnly = true)
    public List<PedidoDto> getAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoDto getPedido(UUID id) {
        return pedidoRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
    }

    @Transactional
    public PedidoDto createPedido(PedidoDto pedidoDto) {
        Usuario usuario = usuarioRepository.findById(pedidoDto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + pedidoDto.getUsuarioId()));

        MetodoDePago metodoDePago = metodoDePagoRepository.findById(pedidoDto.getMetodoDePagoId())
                .orElseThrow(() -> new ResourceNotFoundException("Método de pago no encontrado con id: " + pedidoDto.getMetodoDePagoId()));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDateTime.now());
        pedido.setMontoTotal(pedidoDto.getMontoTotal());
        pedido.setEstado("Pendiente");
        pedido.setDireccionEnvio(pedidoDto.getDireccionEnvio());
        pedido.setMetodoDePago(metodoDePago);

        Pedido savedPedido = pedidoRepository.save(pedido);
        // Registrar actividad en la bitácora
        Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();
        bitacoraService.registrarActividad(usuarioAutenticado, "Creación de pedido: " + savedPedido.getId());

        return convertToDto(savedPedido);
    }

    @Transactional
    public PedidoDto updatePedido(UUID id, PedidoDto pedidoDto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));

        pedido.setMontoTotal(pedidoDto.getMontoTotal());
        pedido.setDireccionEnvio(pedidoDto.getDireccionEnvio());
        Pedido updatedPedido = pedidoRepository.save(pedido);

        // Registrar actividad en la bitácora
        Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();
        bitacoraService.registrarActividad(usuarioAutenticado, "Actualización de pedido: " + updatedPedido.getId());

        return convertToDto(updatedPedido);
    }

    @Transactional
    public PedidoDto confirmarPedido(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));

        pedido.setEstado("Confirmado");
        Pedido updatedPedido = pedidoRepository.save(pedido);

        // Registrar actividad en la bitácora
        Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();
        bitacoraService.registrarActividad(usuarioAutenticado, "Confirmación de pedido: " + updatedPedido.getId());

        return convertToDto(updatedPedido);
    }

    @Transactional
    public void deletePedido(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));

        pedidoRepository.deleteById(id);

        // Registrar actividad en la bitácora
        Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();
        bitacoraService.registrarActividad(usuarioAutenticado, "Eliminación de pedido: " + pedido.getId());

    }

    private PedidoDto convertToDto(Pedido pedido) {
        PedidoDto dto = new PedidoDto();
        dto.setId(pedido.getId());
        dto.setUsuarioId(pedido.getUsuario().getId());
        dto.setFecha(pedido.getFecha());
        dto.setMontoTotal(pedido.getMontoTotal());
        dto.setEstado(pedido.getEstado());
        dto.setDireccionEnvio(pedido.getDireccionEnvio());
        dto.setMetodoDePagoId(pedido.getMetodoDePago().getId());
        return dto;
    }
}


