package com.StyleStore.ecommerce_backend.Service;

import com.StyleStore.ecommerce_backend.Dto.ProductoDto;
import com.StyleStore.ecommerce_backend.Model.Categoria;
import com.StyleStore.ecommerce_backend.Model.Producto;
import com.StyleStore.ecommerce_backend.Model.Subcategoria;
import com.StyleStore.ecommerce_backend.Model.Usuario;
import com.StyleStore.ecommerce_backend.Repository.CategoriaRepository;
import com.StyleStore.ecommerce_backend.Repository.ProductoRepository;
import com.StyleStore.ecommerce_backend.Repository.SubcategoriaRepository;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductoService {


    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private AuthService authService;

        @Autowired
        private ProductoRepository productoRepository;

    @Autowired
    private SubcategoriaRepository subcategoriaRepository; // Cambiado a SubcategoriaRepository


    @Transactional(readOnly = true)
        public List<ProductoDto> getAllProductos() {
            try {
                List<ProductoDto> productos = productoRepository.findAll().stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());


                return productos;
            } catch (Exception e) {
                throw new RuntimeException("Error al obtener todos los productos", e);
            }
        }

    public ProductoDto getProducto(UUID id) {
        try {
            ProductoDto producto = productoRepository.findById(id)
                    .map(this::convertToDto)
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));


            return producto;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el producto con id: " + id, e);
        }
    }

        @Transactional
        public ProductoDto createProducto(ProductoDto productoDto) {
            try {
                Subcategoria subcategoria = subcategoriaRepository.findById(productoDto.getSubcategoriaId())
                        .orElseThrow(() -> new ResourceNotFoundException("Subcategoría no encontrada con id: " + productoDto.getSubcategoriaId()));

                Producto producto = convertToEntity(productoDto);
                producto.setSubcategoria(subcategoria); // Cambiado a setSubcategoria()

                Producto savedProducto = productoRepository.save(producto);


                // Obtener el usuario autenticado y registrar la actividad
                Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();
                bitacoraService.registrarActividad(usuarioAutenticado, "Creó el producto: " + savedProducto.getNombre());

                return convertToDto(savedProducto);
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el producto", e);
            }
        }

        @Transactional
        public ProductoDto updateProducto(UUID id, ProductoDto productoDto) {
            try {
                Producto producto = productoRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

                producto.setNombre(productoDto.getNombre());
                producto.setPrecio(productoDto.getPrecio());
                producto.setDescripcion(productoDto.getDescripcion());

                if (productoDto.getSubcategoriaId() != null) {
                    Subcategoria subcategoria = subcategoriaRepository.findById(productoDto.getSubcategoriaId())
                            .orElseThrow(() -> new ResourceNotFoundException("Subcategoría no encontrada con id: " + productoDto.getSubcategoriaId()));
                    producto.setSubcategoria(subcategoria); // Cambiado a setSubcategoria()
                }

                Producto updatedProducto = productoRepository.save(producto);

                // Obtener el usuario autenticado y registrar la actividad
                Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();
                bitacoraService.registrarActividad(usuarioAutenticado, "Actualizó el producto: " + updatedProducto.getNombre());

                return convertToDto(updatedProducto);
            } catch (ResourceNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar el producto con id: " + id, e);
            }
        }

        @Transactional
        public void deleteProducto(UUID id) {
            try {
                Producto producto = productoRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

                productoRepository.deleteById(id);

                // Obtener el usuario autenticado y registrar la actividad
                Usuario usuarioAutenticado = authService.obtenerUsuarioAutenticado();
                bitacoraService.registrarActividad(usuarioAutenticado, "Eliminó el producto: " + producto.getNombre());
            } catch (ResourceNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar el producto con id: " + id, e);
            }
        }

        private ProductoDto convertToDto(Producto producto) {
            ProductoDto dto = new ProductoDto();
            dto.setId(producto.getId());
            dto.setNombre(producto.getNombre());
            dto.setPrecio(producto.getPrecio());
            dto.setDescripcion(producto.getDescripcion());
            dto.setSubcategoriaId(producto.getSubcategoria().getId()); // Asignar el ID de la subcateg al DTO
            return dto;
        }

        private Producto convertToEntity(ProductoDto dto) {
            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setPrecio(dto.getPrecio());
            producto.setDescripcion(dto.getDescripcion());
            return producto;
        }


}
