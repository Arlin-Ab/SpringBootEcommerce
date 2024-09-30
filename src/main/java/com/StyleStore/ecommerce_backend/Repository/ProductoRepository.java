package com.StyleStore.ecommerce_backend.Repository;
import com.StyleStore.ecommerce_backend.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;
public interface ProductoRepository extends JpaRepository<Producto, UUID> {

}
