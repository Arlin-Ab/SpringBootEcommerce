package com.StyleStore.ecommerce_backend.Repository;

import com.StyleStore.ecommerce_backend.Model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, UUID> {
    List<ItemCarrito> findByPedidoId(UUID pedidoId);
}
