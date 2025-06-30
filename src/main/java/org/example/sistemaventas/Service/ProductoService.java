package org.example.sistemaventas.Service;

import org.example.sistemaventas.Model.Producto;
import org.example.sistemaventas.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }
    public Producto update(Long productoId, int cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setStockActual(producto.getStockActual() + cantidad);
        return productoRepository.save(producto);
    }
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }
}