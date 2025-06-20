package com.example.sistemaventas.repository;

import com.example.sistemaventas.model.InventarioMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventarioMovimientoRepository extends JpaRepository<InventarioMovimiento, Long> {
    List<InventarioMovimiento> findByProductoId(Long productoId);
    List<InventarioMovimiento> findByTipoMovimiento(String tipo); // Ej: "ENTRADA", "SALIDA"
}