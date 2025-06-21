package org.example.sistemaventas.Service;

import org.example.sistemaventas.Model.DetalleVenta;
import org.example.sistemaventas.Repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetalleVentaService {
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<DetalleVenta> findByVentaId(Long ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId);
    }

    public DetalleVenta save(DetalleVenta detalle) {
        return detalleVentaRepository.save(detalle);
    }

    public void deleteDetalleVentaById(Long ventaId) {
        detalleVentaRepository.deleteById(ventaId);
    }
}