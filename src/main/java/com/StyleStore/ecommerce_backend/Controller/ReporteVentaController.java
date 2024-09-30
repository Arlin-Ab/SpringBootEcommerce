package com.StyleStore.ecommerce_backend.Controller;

import com.StyleStore.ecommerce_backend.Dto.ReporteVentaDto;
import com.StyleStore.ecommerce_backend.Service.ReporteVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reportes")
public class ReporteVentaController {

    @Autowired
    private ReporteVentaService reporteVentaService;

    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<List<ReporteVentaDto>> getReportePorSucursal(@PathVariable UUID sucursalId,
                                                                       @RequestParam LocalDate startDate,
                                                                       @RequestParam LocalDate endDate) {
        try {
            return ResponseEntity.ok(reporteVentaService.getReportePorSucursal(sucursalId, startDate, endDate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/global")
    public ResponseEntity<BigDecimal> getReporteGlobal(@RequestParam LocalDate startDate,
                                                       @RequestParam LocalDate endDate) {
        try {
            return ResponseEntity.ok(reporteVentaService.getReporteGlobal(startDate, endDate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

