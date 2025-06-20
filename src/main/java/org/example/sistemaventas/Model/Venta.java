package com.example.sistemaventas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long venta_id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    @Column(precision = 10, scale = 2)
    private Double subtotal;

    @Column(precision = 10, scale = 2)
    private Double impuesto;

    @Column(precision = 10, scale = 2)
    private Double descuento;

    @Column(precision = 10, scale = 2)
    private Double total;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago; // Enum: EFECTIVO, TARJETA, etc.

    @Enumerated(EnumType.STRING)
    private EstadoVenta estado; // Enum: COMPLETADA, CANCELADA, etc.

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;

    // Getters y Setters
}