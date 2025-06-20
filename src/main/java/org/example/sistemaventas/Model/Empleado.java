package com.example.sistemaventas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empleado_id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(length = 150, unique = true)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @Enumerated(EnumType.STRING)
    private EstadoEmpleado estado; // Enum: ACTIVO, INACTIVO, etc.

    // Getters y Setters
}