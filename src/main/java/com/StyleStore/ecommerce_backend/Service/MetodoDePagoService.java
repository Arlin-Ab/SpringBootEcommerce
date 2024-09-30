package com.StyleStore.ecommerce_backend.Service;

import com.StyleStore.ecommerce_backend.Dto.MetodoDePagoDto;
import com.StyleStore.ecommerce_backend.Model.MetodoDePago;
import com.StyleStore.ecommerce_backend.Repository.MetodoDePagoRepository;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MetodoDePagoService {

    @Autowired
    private MetodoDePagoRepository metodoDePagoRepository;

    @Transactional(readOnly = true)
    public List<MetodoDePagoDto> getAllMetodosDePago() {
        return metodoDePagoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MetodoDePagoDto getMetodoDePago(UUID id) {
        return metodoDePagoRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Método de pago no encontrado con id: " + id));
    }

    @Transactional
    public MetodoDePagoDto createMetodoDePago(MetodoDePagoDto metodoDePagoDto) {
        MetodoDePago metodoDePago = convertToEntity(metodoDePagoDto);
        MetodoDePago savedMetodoDePago = metodoDePagoRepository.save(metodoDePago);
        return convertToDto(savedMetodoDePago);
    }

    @Transactional
    public MetodoDePagoDto updateMetodoDePago(UUID id, MetodoDePagoDto metodoDePagoDto) {
        MetodoDePago metodoDePago = metodoDePagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Método de pago no encontrado con id: " + id));

        metodoDePago.setNombre(metodoDePagoDto.getNombre());
        metodoDePago.setDescripcion(metodoDePagoDto.getDescripcion());

        MetodoDePago updatedMetodoDePago = metodoDePagoRepository.save(metodoDePago);
        return convertToDto(updatedMetodoDePago);
    }

    @Transactional
    public void deleteMetodoDePago(UUID id) {
        if (!metodoDePagoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Método de pago no encontrado con id: " + id);
        }
        metodoDePagoRepository.deleteById(id);
    }

    private MetodoDePagoDto convertToDto(MetodoDePago metodoDePago) {
        MetodoDePagoDto dto = new MetodoDePagoDto();
        dto.setId(metodoDePago.getId());
        dto.setNombre(metodoDePago.getNombre());
        dto.setDescripcion(metodoDePago.getDescripcion());
        return dto;
    }

    private MetodoDePago convertToEntity(MetodoDePagoDto dto) {
        MetodoDePago metodoDePago = new MetodoDePago();
        metodoDePago.setNombre(dto.getNombre());
        metodoDePago.setDescripcion(dto.getDescripcion());
        return metodoDePago;
    }
}

