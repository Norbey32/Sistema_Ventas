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

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Transactional
    public Venta crearVenta(Venta venta, List<DetalleVenta> detalles) {
        // Obtener referencias completas de cliente y empleado
        if (venta.getCliente() != null && venta.getCliente().getId() != null) {
            Cliente cliente = clienteRepository.findById(venta.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            venta.setCliente(cliente);
        }

        if (venta.getEmpleado() != null && venta.getEmpleado().getId() != null) {
            Empleado empleado = empleadoRepository.findById(venta.getEmpleado().getId())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
            venta.setEmpleado(empleado);
        }

        BigDecimal subtotal = BigDecimal.ZERO;

        for (DetalleVenta detalle : detalles) {
            // Obtener producto completo por ID
            if (detalle.getProducto() != null && detalle.getProducto().getId() != null) {
                Producto producto = productoService.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                detalle.setProducto(producto);

                int cantidad = detalle.getCantidad();
                if (producto.getStockActual() < cantidad) {
                    throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
                }

                // Actualizar stock
                productoService.updateStock(producto.getId(), -cantidad);

                BigDecimal precioUnitario = detalle.getPrecioUnitario();
                BigDecimal subtotalDetalle = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
                detalle.setSubtotal(subtotalDetalle);
                subtotal = subtotal.add(subtotalDetalle);
            }
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

    @Transactional
    public Venta actualizarVenta(Long ventaId, Venta ventaActualizada, List<DetalleVenta> nuevosDetalles) {
        // Buscar la venta existente
        Venta ventaExistente = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // Revertir stock de los detalles anteriores
        List<DetalleVenta> detallesAnteriores = detalleVentaRepository.findByVenta_Id(ventaId);
        for (DetalleVenta detalleAnterior : detallesAnteriores) {
            productoService.updateStock(detalleAnterior.getProducto().getId(), detalleAnterior.getCantidad());
        }

        // Eliminar detalles anteriores
        detalleVentaRepository.deleteAll(detallesAnteriores);

        // Actualizar campos básicos
        if (ventaActualizada.getCliente() != null && ventaActualizada.getCliente().getId() != null) {
            Cliente cliente = clienteRepository.findById(ventaActualizada.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            ventaExistente.setCliente(cliente);
        }

        if (ventaActualizada.getEmpleado() != null && ventaActualizada.getEmpleado().getId() != null) {
            Empleado empleado = empleadoRepository.findById(ventaActualizada.getEmpleado().getId())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
            ventaExistente.setEmpleado(empleado);
        }

        ventaExistente.setImpuesto(ventaActualizada.getImpuesto());
        ventaExistente.setDescuento(ventaActualizada.getDescuento());
        ventaExistente.setMetodoPago(ventaActualizada.getMetodoPago());

        // Procesar nuevos detalles
        BigDecimal subtotal = BigDecimal.ZERO;

        for (DetalleVenta detalle : nuevosDetalles) {
            if (detalle.getProducto() != null && detalle.getProducto().getId() != null) {
                Producto producto = productoService.findById(detalle.getProducto().getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                detalle.setProducto(producto);

                int cantidad = detalle.getCantidad();
                if (producto.getStockActual() < cantidad) {
                    throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
                }

                // Actualizar stock
                productoService.updateStock(producto.getId(), -cantidad);

                BigDecimal precioUnitario = detalle.getPrecioUnitario();
                BigDecimal subtotalDetalle = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
                detalle.setSubtotal(subtotalDetalle);
                subtotal = subtotal.add(subtotalDetalle);

                // Asignar la venta al detalle
                detalle.setVenta(ventaExistente);
            }
        }

        // Actualizar totales
        ventaExistente.setSubtotal(subtotal);
        BigDecimal total = subtotal
                .add(ventaExistente.getImpuesto() != null ? ventaExistente.getImpuesto() : BigDecimal.ZERO)
                .subtract(ventaExistente.getDescuento() != null ? ventaExistente.getDescuento() : BigDecimal.ZERO);
        ventaExistente.setTotal(total);

        // Guardar venta actualizada
        Venta ventaGuardada = ventaRepository.save(ventaExistente);

        // Guardar nuevos detalles
        detalleVentaRepository.saveAll(nuevosDetalles);

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