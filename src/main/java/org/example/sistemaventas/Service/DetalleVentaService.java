package org.example.sistemaventas.Service;

import org.example.sistemaventas.Model.DetalleVenta;
import org.example.sistemaventas.Repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public DetalleVenta save(DetalleVenta detalle) {
        // Calcula el subtotal si es necesario
        if (detalle.getPrecioUnitario() != null && detalle.getCantidad() != null) {
            BigDecimal subtotal = detalle.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalle.getCantidad()));
            detalle.setSubtotal(subtotal);
        }
        return detalleVentaRepository.save(detalle);
    }
    public List<DetalleVenta> findAll() {
        return detalleVentaRepository.findAll();
    }

    public List<DetalleVenta> findByVentaId(Long ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId);
    }

    public void deleteDetalleVentaById(Long ventaId) {
        detalleVentaRepository.deleteById(ventaId);
    }
}