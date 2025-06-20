package com.example.sistemaventas.service;

import com.example.sistemaventas.model.*;
import com.example.sistemaventas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private ProductoService productoService;

    @Transactional
    public Venta crearVenta(Venta venta, List<DetalleVenta> detalles) {
        // Validar stock y calcular totales
        double subtotal = 0;
        for (DetalleVenta detalle : detalles) {
            Producto producto = detalle.getProducto();
            int cantidad = detalle.getCantidad();

            if (producto.getStockActual() < cantidad) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            // Actualizar stock
            productoService.updateStock(producto.getProductoId(), -cantidad);

            // Calcular subtotal del detalle
            detalle.setSubtotal(detalle.getPrecioUnitario() * cantidad);
            subtotal += detalle.getSubtotal();
        }

        // Asignar valores a la venta
        venta.setFechaVenta(LocalDateTime.now());
        venta.setSubtotal(subtotal);
        venta.setTotal(subtotal + venta.getImpuesto() - venta.getDescuento());
        venta.setEstado("COMPLETADA");

        // Guardar venta y detalles
        Venta ventaGuardada = ventaRepository.save(venta);
        detalles.forEach(detalle -> detalle.setVenta(ventaGuardada));
        detalleVentaRepository.saveAll(detalles);

        return ventaGuardada;
    }
}