package org.example.sistemaventas.Repository;

import org.example.sistemaventas.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByStockActualLessThan(int stockMinimo); // Para alertas de stock bajo
    Producto findByCodigo(String codigo);
}