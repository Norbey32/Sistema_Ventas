package com.sistema.ventas.controller;

import com.sistema.ventas.model.InventarioMovimiento;
import com.sistema.ventas.service.InventarioMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario-movimientos")
public class InventarioMovimientoController {

    @Autowired
    private InventarioMovimientoService inventarioMovimientoService;

    @GetMapping
    public ResponseEntity<List<InventarioMovimiento>> obtenerTodosMovimientos() {
        return ResponseEntity.ok(inventarioMovimientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioMovimiento> obtenerMovimientoPorId(@PathVariable Integer id) {
        InventarioMovimiento movimiento = inventarioMovimientoService.findById(id);
        if (movimiento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping
    public ResponseEntity<InventarioMovimiento> crearMovimiento(
            @RequestBody InventarioMovimiento movimiento) {
        return ResponseEntity.ok(inventarioMovimientoService.save(movimiento));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<InventarioMovimiento>> obtenerMovimientosPorProducto(
            @PathVariable Integer productoId) {
        return ResponseEntity.ok(inventarioMovimientoService.findByProductoId(productoId));
    }
}