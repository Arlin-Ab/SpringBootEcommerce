package com.StyleStore.ecommerce_backend.Service;

import com.StyleStore.ecommerce_backend.Dto.ReporteVentaDto;
import com.StyleStore.ecommerce_backend.Model.ReporteVenta;
import com.StyleStore.ecommerce_backend.Repository.ReporteVentaRepository;
import com.StyleStore.ecommerce_backend.Repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReporteVentaService {

    @Autowired
    private ReporteVentaRepository reporteVentaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Transactional(readOnly = true)
    public List<ReporteVentaDto> getReportePorSucursal(UUID sucursalId, LocalDate startDate, LocalDate endDate) {
        try {
            return reporteVentaRepository.findBySucursalIdAndFechaInicioBetween(sucursalId, startDate, endDate)
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los reportes de ventas", e);
        }
    }

    @Transactional(readOnly = true)
    public BigDecimal getReporteGlobal(LocalDate startDate, LocalDate endDate) {
        try {
            // Obtener todas las sucursales y sumar las ventas de cada una
            return reporteVentaRepository.findAll().stream()
                    .filter(reporte -> !reporte.getFechaInicio().isBefore(startDate) &&
                            !reporte.getFechaFin().isAfter(endDate))
                    .map(ReporteVenta::getTotalVentas)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el reporte global", e);
        }
    }

    private ReporteVentaDto convertToDto(ReporteVenta reporte) {
        ReporteVentaDto dto = new ReporteVentaDto();
        dto.setId(reporte.getId());
        dto.setFechaInicio(reporte.getFechaInicio());
        dto.setFechaFin(reporte.getFechaFin());
        dto.setTotalVentas(reporte.getTotalVentas());
        dto.setSucursalId(reporte.getSucursal().getId());
        return dto;
    }
}

