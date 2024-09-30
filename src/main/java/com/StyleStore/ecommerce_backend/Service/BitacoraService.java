package com.StyleStore.ecommerce_backend.Service;

import com.StyleStore.ecommerce_backend.Dto.BitacoraDto;
import com.StyleStore.ecommerce_backend.Model.Bitacora;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.BitacoraRepository;
import com.StyleStore.ecommerce_backend.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void registrarActividad(Usuario usuario, String actividad) {
        Bitacora bitacora = new Bitacora();
        bitacora.setUsuario(usuario);
        bitacora.setActividad(actividad);
        bitacora.setFechaHora(LocalDateTime.now());
        bitacoraRepository.save(bitacora);
    }

    @Transactional(readOnly = true)
    public List<BitacoraDto> getAllBitacoras() {
        return bitacoraRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BitacoraDto convertToDto(Bitacora bitacora) {
        BitacoraDto dto = new BitacoraDto();
        dto.setId(bitacora.getId());
        dto.setUsuarioId(bitacora.getUsuario().getId());
        dto.setActividad(bitacora.getActividad());
        dto.setFechaHora(bitacora.getFechaHora());
        return dto;
    }
}

