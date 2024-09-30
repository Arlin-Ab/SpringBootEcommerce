package com.StyleStore.ecommerce_backend.Repository;

import com.StyleStore.ecommerce_backend.Model.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, UUID> {
}

