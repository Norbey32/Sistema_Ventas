package org.example.sistemaventas.Service;

import org.example.sistemaventas.Model.Producto;
import org.example.sistemaventas.Model.Categoria;
import org.example.sistemaventas.Model.Proveedor;
import org.example.sistemaventas.Repository.ProductoRepository;
import org.example.sistemaventas.Repository.CategoriaRepository;
import org.example.sistemaventas.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public List<Producto> findProductosConStockBajo() {
        return productoRepository.findByStockActualLessThan(10);
    }

    public Producto save(Producto producto) {
        // Obtener referencias a las entidades relacionadas si existen
        if (producto.getCategoria() != null && producto.getCategoria().getCategoria_id() != null) {
            Categoria categoria = categoriaRepository.findById(producto.getCategoria().getCategoria_id())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        }

        if (producto.getProveedor() != null && producto.getProveedor().getProveedor_id() != null) {
            Proveedor proveedor = proveedorRepository.findById(producto.getProveedor().getProveedor_id())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
            producto.setProveedor(proveedor);
        }

        return productoRepository.save(producto);
    }

    public Producto update(Long productoId, Producto productoActualizado) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualizar campos básicos
        producto.setCodigo(productoActualizado.getCodigo());
        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecioUnitario(productoActualizado.getPrecioUnitario());
        producto.setStockActual(productoActualizado.getStockActual());
        producto.setStockMinimo(productoActualizado.getStockMinimo());
        producto.setEstado(productoActualizado.getEstado());

        // Actualizar categoría si se proporciona
        if (productoActualizado.getCategoria() != null && productoActualizado.getCategoria().getCategoria_id() != null) {
            Categoria categoria = categoriaRepository.findById(productoActualizado.getCategoria().getCategoria_id())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        }

        // Actualizar proveedor si se proporciona
        if (productoActualizado.getProveedor() != null && productoActualizado.getProveedor().getProveedor_id() != null) {
            Proveedor proveedor = proveedorRepository.findById(productoActualizado.getProveedor().getProveedor_id())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
            producto.setProveedor(proveedor);
        }

        return productoRepository.save(producto);
    }

    // Método original para actualizar solo stock
    public Producto updateStock(Long productoId, int cantidad) {
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