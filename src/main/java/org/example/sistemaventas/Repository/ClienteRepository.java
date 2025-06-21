package org.example.sistemaventas.Repository;

import org.example.sistemaventas.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Consultas personalizadas (si las necesitas)
    Cliente findByEmail(String email);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}