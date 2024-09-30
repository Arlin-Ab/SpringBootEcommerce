package com.StyleStore.ecommerce_backend.Service;

import com.StyleStore.ecommerce_backend.Dto.CreateSubcategoriaDto;
import com.StyleStore.ecommerce_backend.Dto.SubcategoriaDto;
import com.StyleStore.ecommerce_backend.Model.Categoria;
import com.StyleStore.ecommerce_backend.Model.Subcategoria;
import com.StyleStore.ecommerce_backend.Repository.CategoriaRepository;
import com.StyleStore.ecommerce_backend.Repository.SubcategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubcategoriaService {

    @Autowired
    private SubcategoriaRepository subcategoriaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Obtener todas las subcategorías
    @Transactional(readOnly = true)
    public List<SubcategoriaDto> getAllSubcategorias() {
        try {
            return subcategoriaRepository.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las subcategorías", e);
        }
    }

    // Crear una nueva subcategoría
    @Transactional
    public SubcategoriaDto createSubcategoria(CreateSubcategoriaDto dto) {
        try {
            Subcategoria subcategoria = new Subcategoria();
            subcategoria.setNombre(dto.getNombre());
            subcategoria.setDescripcion(dto.getDescripcion());

            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            subcategoria.setCategoria(categoria);

            subcategoria = subcategoriaRepository.save(subcategoria);
            return convertToDto(subcategoria);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la subcategoría", e);
        }
    }

    // Actualizar una subcategoría existente
    @Transactional
    public SubcategoriaDto updateSubcategoria(UUID id, CreateSubcategoriaDto dto) {
        try {
            Subcategoria subcategoria = subcategoriaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

            subcategoria.setNombre(dto.getNombre());
            subcategoria.setDescripcion(dto.getDescripcion());

            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            subcategoria.setCategoria(categoria);

            subcategoria = subcategoriaRepository.save(subcategoria);
            return convertToDto(subcategoria);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la subcategoría", e);
        }
    }

    // Eliminar una subcategoría
    @Transactional
    public void deleteSubcategoria(UUID id) {
        try {
            subcategoriaRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la subcategoría", e);
        }
    }

    // Conversión de entidad a DTO
    private SubcategoriaDto convertToDto(Subcategoria subcategoria) {
        SubcategoriaDto dto = new SubcategoriaDto();
        dto.setId(subcategoria.getId());
        dto.setNombre(subcategoria.getNombre());
        dto.setDescripcion(subcategoria.getDescripcion());
        dto.setCategoriaId(subcategoria.getCategoria().getId());
        return dto;
    }
}

