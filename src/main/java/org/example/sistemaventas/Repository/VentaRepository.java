package com.example.sistemaventas.repository;

import com.example.sistemaventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByFechaVentaBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Venta> findByClienteId(Long clienteId);
    List<Venta> findByEstado(String estado); // Ej: "COMPLETADA", "CANCELADA"
}