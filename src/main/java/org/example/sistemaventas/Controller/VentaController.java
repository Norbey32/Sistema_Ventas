package com.example.sistemaventas.controller;

import com.example.sistemaventas.model.Venta;
import com.example.sistemaventas.model.DetalleVenta;
import com.example.sistemaventas.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<Venta> crearVenta(
            @RequestBody Venta venta,
            @RequestParam List<DetalleVenta> detalles) {
        return ResponseEntity.ok(ventaService.crearVenta(venta, detalles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Venta>> getVentasPorFecha(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        // Implementar conversión de String a LocalDateTime
        return ResponseEntity.ok(ventaService.findByFechaBetween(fechaInicio, fechaFin));
    }
}