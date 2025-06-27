package org.example.sistemaventas.Controller;

import org.example.sistemaventas.Model.DetalleVenta;
import org.example.sistemaventas.Service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalle-ventas")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @GetMapping
    public ResponseEntity<List<DetalleVenta>> getAllDetalleVentas() {
        return ResponseEntity.ok(detalleVentaService.findAll());
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<DetalleVenta>> obtenerDetallesPorVenta(
        @PathVariable Long ventaId) {
    return ResponseEntity.ok(detalleVentaService.findById(ventaId));
}

    @PostMapping
    public ResponseEntity<DetalleVenta> crearDetalleVenta(
            @RequestBody DetalleVenta detalleVenta) {
        return ResponseEntity.ok(detalleVentaService.save(detalleVenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<DetalleVenta>> actualizarDetalleVenta(
            @PathVariable Long id,
            @RequestBody DetalleVenta detalleVenta) {
        return ResponseEntity.ok(detalleVentaService.update( id, detalleVenta));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleVenta(@PathVariable Long id) {
        detalleVentaService.deleteDetalleVentaById(id);
        return ResponseEntity.noContent().build();
    }
}