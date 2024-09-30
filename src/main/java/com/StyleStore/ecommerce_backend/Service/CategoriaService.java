package com.StyleStore.ecommerce_backend.Service;

import com.StyleStore.ecommerce_backend.Dto.CategoriaDto;
import com.StyleStore.ecommerce_backend.Model.Categoria;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.CategoriaRepository;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoriaService {


    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private AuthService authService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaDto> getAllCategorias() {
        try {
            return categoriaRepository.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las categorías", e);
        }
    }

    @Transactional(readOnly = true)
    public CategoriaDto getCategoria(UUID id) {
        try {
            return categoriaRepository.findById(id)
                    .map(this::convertToDto)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la categoría con id: " + id, e);
        }
    }

    @Transactional
    public CategoriaDto createCategoria(CategoriaDto categoriaDto) {
        try {
            Categoria categoria = convertToEntity(categoriaDto);
            Categoria savedCategoria = categoriaRepository.save(categoria);

            // Registrar actividad en la bitácora
            Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();
            bitacoraService.registrarActividad(usuarioAutenticado, "Creación de categoría: " + savedCategoria.getNombre());

            return convertToDto(savedCategoria);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la categoría", e);
        }
    }

    @Transactional
    public CategoriaDto updateCategoria(UUID id, CategoriaDto categoriaDto) {
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

            categoria.setNombre(categoriaDto.getNombre());
            categoria.setDescripcion(categoriaDto.getDescripcion());

            Categoria updatedCategoria = categoriaRepository.save(categoria);
            // Registrar actividad en la bitácora
            Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();
            bitacoraService.registrarActividad(usuarioAutenticado, "Actualización de categoría: " + updatedCategoria.getNombre());

            return convertToDto(updatedCategoria);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la categoría con id: " + id, e);
        }
    }

    @Transactional
    public void deleteCategoria(UUID id) {
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

            categoriaRepository.deleteById(id);

            // Registrar actividad en la bitácora
            Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();
            bitacoraService.registrarActividad(usuarioAutenticado, "Eliminación de categoría: " + categoria.getNombre());

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la categoría con id: " + id, e);
        }
    }

    private CategoriaDto convertToDto(Categoria categoria) {
        CategoriaDto dto = new CategoriaDto();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }

    private Categoria convertToEntity(CategoriaDto dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }
}
