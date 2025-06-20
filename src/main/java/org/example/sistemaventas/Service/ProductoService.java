package com.example.sistemaventas.service;

import com.example.sistemaventas.model.Producto;
import com.example.sistemaventas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public List<Producto> findProductosConStockBajo() {
        return productoRepository.findByStockActualLessThan(10); // Ej: stock mínimo = 10
    }

    public Producto updateStock(Long productoId, int cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setStockActual(producto.getStockActual() + cantidad);
        return productoRepository.save(producto);
    }
}