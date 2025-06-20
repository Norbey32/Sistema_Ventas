package com.example.sistemaventas.service;

import com.example.sistemaventas.model.*;
import com.example.sistemaventas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class InventarioMovimientoService {
    @Autowired
    private InventarioMovimientoRepository movimientoRepository;
    @Autowired
    private ProductoService productoService;

    @Transactional
    public InventarioMovimiento registrarMovimiento(Long productoId, String tipo, int cantidad, String nota) {
        Producto producto = productoService.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualizar stock según el tipo de movimiento
        if (tipo.equals("ENTRADA")) {
            productoService.updateStock(productoId, cantidad);
        } else if (tipo.equals("SALIDA")) {
            productoService.updateStock(productoId, -cantidad);
        }

        // Registrar movimiento
        InventarioMovimiento movimiento = new InventarioMovimiento();
        movimiento.setProducto(producto);
        movimiento.setTipoMovimiento(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setFechaMovimiento(LocalDateTime.now());
        movimiento.setNota(nota);

        return movimientoRepository.save(movimiento);
    }
}