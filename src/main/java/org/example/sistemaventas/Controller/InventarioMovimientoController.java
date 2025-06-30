package org.example.sistemaventas.Controller;

import org.example.sistemaventas.Model.InventarioMovimiento;
import org.example.sistemaventas.Service.InventarioMovimientoService;
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
    public ResponseEntity<InventarioMovimiento> obtenerMovimientoPorId(@PathVariable Long id) {
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

    @PutMapping("/{id}")
    public ResponseEntity<InventarioMovimiento> actualizarMovimiento(
            @PathVariable Long id,
            @RequestBody InventarioMovimiento inventariomovimiento) {
        return ResponseEntity.ok(inventarioMovimientoService.update(id, inventariomovimiento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        inventarioMovimientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}