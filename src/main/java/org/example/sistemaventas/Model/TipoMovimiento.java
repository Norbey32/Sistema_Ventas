package org.example.sistemaventas.Model;

public enum TipoMovimiento {
    ENTRADA,    // Cuando ingresa inventario (compra, devolución)
    SALIDA,     // Cuando sale inventario (venta, daño)
    AJUSTE,     // Corrección de inventario
    TRANSFERENCIA,  // Movimiento entre almacenes
    DEVOLUCION  // Devolución de cliente
}