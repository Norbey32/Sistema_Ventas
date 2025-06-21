package org.example.sistemaventas.Service;

import org.example.sistemaventas.Model.Proveedor;
import org.example.sistemaventas.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    public Proveedor findById(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    public Proveedor save(Proveedor proveedor) {
        // Validar email único
        if (proveedorRepository.findByEmail(proveedor.getEmail()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }
        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> searchByNombre(String nombre) {
        return proveedorRepository.findByNombreContainingIgnoreCase(nombre);
    }
}