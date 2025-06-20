package com.example.sistemaventas.service;

import com.example.sistemaventas.model.DetalleVenta;
import com.example.sistemaventas.repository.DetalleVentaRepository;
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

    public void deleteByVentaId(Long ventaId) {
        detalleVentaRepository.deleteByVentaId(ventaId);
    }
}