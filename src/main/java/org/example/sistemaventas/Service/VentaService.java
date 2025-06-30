package org.example.sistemaventas.Service;

import org.example.sistemaventas.Model.*;
import org.example.sistemaventas.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        BigDecimal subtotal = BigDecimal.ZERO;

        for (DetalleVenta detalle : detalles) {
            Producto producto = detalle.getProducto();
            int cantidad = detalle.getCantidad();

            if (producto.getStockActual() < cantidad) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            productoService.update(producto.getId(), -cantidad);

            // Usa directamente el BigDecimal o convierte adecuadamente
            BigDecimal precioUnitario = detalle.getPrecioUnitario(); // Si ya es BigDecimal

            BigDecimal subtotalDetalle = precioUnitario.multiply(BigDecimal.valueOf(cantidad));

            // Actualiza esto también (evita doubleValue() si el campo es BigDecimal):
            detalle.setSubtotal(subtotalDetalle); // Cambia a BigDecimal en DetalleVenta

            subtotal = subtotal.add(subtotalDetalle);
        }

        venta.setFechaVenta(LocalDateTime.now());
        venta.setSubtotal(subtotal);

        BigDecimal total = subtotal
                .add(venta.getImpuesto() != null ? venta.getImpuesto() : BigDecimal.ZERO)
                .subtract(venta.getDescuento() != null ? venta.getDescuento() : BigDecimal.ZERO);
        venta.setTotal(total);

        venta.setEstado(EstadoVenta.COMPLETADA);

        Venta ventaGuardada = ventaRepository.save(venta);
        detalles.forEach(detalle -> detalle.setVenta(ventaGuardada));
        detalleVentaRepository.saveAll(detalles);

        return ventaGuardada;
    }
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    public Venta findById(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    public void delete(Long id) {
        ventaRepository.deleteById(id);
    }
}
