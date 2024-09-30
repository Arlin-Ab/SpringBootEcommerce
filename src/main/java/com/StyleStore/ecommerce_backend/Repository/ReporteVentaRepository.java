package com.StyleStore.ecommerce_backend.Repository;

import com.StyleStore.ecommerce_backend.Model.ReporteVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReporteVentaRepository extends JpaRepository<ReporteVenta, UUID> {
    List<ReporteVenta> findBySucursalIdAndFechaInicioBetween(UUID sucursalId, LocalDate startDate, LocalDate endDate);
}

