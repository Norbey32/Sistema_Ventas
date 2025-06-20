package com.example.sistemaventas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cliente_id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(length = 150, unique = true)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column(length = 200)
    private String direccion;

    @Column(length = 100)
    private String ciudad;

    @Column(length = 100)
    private String estado;

    @Column(name = "codigo_postal", length = 20)
    private String codigoPostal;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    // Getters y Setters (usa Lombok si prefieres)
    public Long getCliente_id() { return cliente_id; }
    public void setCliente_id(Long cliente_id) { this.cliente_id = cliente_id; }
    // ... (otros getters y setters)
}