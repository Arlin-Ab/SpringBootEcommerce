package com.StyleStore.ecommerce_backend.Service;


import com.StyleStore.ecommerce_backend.Dto.ItemCarritoDto;
import com.StyleStore.ecommerce_backend.Model.ItemCarrito;
import com.StyleStore.ecommerce_backend.Model.Producto;
import com.StyleStore.ecommerce_backend.Repository.CarritoRepository;
import com.StyleStore.ecommerce_backend.Repository.ItemCarritoRepository;
import com.StyleStore.ecommerce_backend.Repository.PedidoRepository;
import com.StyleStore.ecommerce_backend.Repository.ProductoRepository;
import com.StyleStore.ecommerce_backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ItemCarritoService {

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Obtener todos los ítems del carrito
    @Transactional(readOnly = true)
    public List<ItemCarritoDto> getAllItems() {
        try {
            return itemCarritoRepository.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todos los ítems del carrito", e);
        }
    }

    // Obtener ítem por ID
    @Transactional(readOnly = true)
    public ItemCarritoDto getItemById(UUID id) {
        try {
            return itemCarritoRepository.findById(id)
                    .map(this::convertToDto)
                    .orElseThrow(() -> new ResourceNotFoundException("Item no encontrado con id: " + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el ítem del carrito con id: " + id, e);
        }
    }

    // Añadir ítem al carrito
    @Transactional
    public ItemCarritoDto addItem(ItemCarritoDto itemCarritoDto) {
        try {
            // Obtener producto para validar el stock
            Producto producto = productoRepository.findById(itemCarritoDto.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + itemCarritoDto.getProductoId()));

            // Validar si hay suficiente stock
            if (producto.getStock() < itemCarritoDto.getCantidad()) {
                throw new IllegalArgumentException("La cantidad solicitada excede el stock disponible.");
            }

            // Crear el ítem del carrito y guardar
            ItemCarrito itemCarrito = convertToEntity(itemCarritoDto);
            itemCarrito.setProducto(producto);
            ItemCarrito savedItemCarrito = itemCarritoRepository.save(itemCarrito);

            // Restar el stock después de agregar al carrito
            producto.setStock(producto.getStock() - itemCarritoDto.getCantidad());
            productoRepository.save(producto);

            return convertToDto(savedItemCarrito);
        } catch (Exception e) {
            throw new RuntimeException("Error al agregar el ítem al carrito", e);
        }
    }

    // Actualizar ítem en el carrito
    @Transactional
    public ItemCarritoDto updateItem(UUID id, ItemCarritoDto itemCarritoDto) {
        try {
            ItemCarrito itemCarrito = itemCarritoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Item no encontrado con id: " + id));

            // Validar stock para la cantidad actualizada
            Producto producto = itemCarrito.getProducto();
            if (producto.getStock() < itemCarritoDto.getCantidad()) {
                throw new IllegalArgumentException("La cantidad solicitada excede el stock disponible.");
            }

            // Actualizar la cantidad en el ítem
            itemCarrito.setCantidad(itemCarritoDto.getCantidad());
            ItemCarrito updatedItem = itemCarritoRepository.save(itemCarrito);

            return convertToDto(updatedItem);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el ítem del carrito con id: " + id, e);
        }
    }

    // Eliminar ítem del carrito
    @Transactional
    public void deleteItem(UUID id) {
        try {
            if (!itemCarritoRepository.existsById(id)) {
                throw new ResourceNotFoundException("Item no encontrado con id: " + id);
            }
            itemCarritoRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el ítem del carrito con id: " + id, e);
        }
    }

    // Conversión de entidad a DTO
    private ItemCarritoDto convertToDto(ItemCarrito itemCarrito) {
        ItemCarritoDto dto = new ItemCarritoDto();
        dto.setId(itemCarrito.getId());
        dto.setCarritoId(itemCarrito.getCarrito().getId());
        dto.setProductoId(itemCarrito.getProducto().getId());
        dto.setPedidoId(itemCarrito.getPedido().getId());
        dto.setCantidad(itemCarrito.getCantidad());
        return dto;
    }

    // Conversión de DTO a entidad
    private ItemCarrito convertToEntity(ItemCarritoDto dto) {
        try {
            ItemCarrito itemCarrito = new ItemCarrito();
            itemCarrito.setCarrito(carritoRepository.findById(dto.getCarritoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado")));
            itemCarrito.setProducto(productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado")));
            itemCarrito.setPedido(pedidoRepository.findById(dto.getPedidoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado")));
            itemCarrito.setCantidad(dto.getCantidad());
            return itemCarrito;
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el DTO a entidad", e);
        }
    }
}


