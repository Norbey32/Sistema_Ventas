package com.example.sistemaventas.repository;

import com.example.sistemaventas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Consultas personalizadas (si las necesitas)
    Cliente findByEmail(String email);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}