package org.example.sistemaventas.Repository;

import org.example.sistemaventas.Model.InventarioMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventarioMovimientoRepository extends JpaRepository<InventarioMovimiento, Long> {
    List<InventarioMovimiento> findByProductoId(Long productoId);
    List<InventarioMovimiento> findByMovimientoId(String tipo); // Ej: "ENTRADA", "SALIDA"
}