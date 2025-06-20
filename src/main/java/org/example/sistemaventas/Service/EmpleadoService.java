package com.example.sistemaventas.service;

import com.example.sistemaventas.model.Empleado;
import com.example.sistemaventas.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    public Empleado findById(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    public Empleado save(Empleado empleado) {
        // Validar email único
        if (empleadoRepository.findByEmail(empleado.getEmail()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }
        return empleadoRepository.save(empleado);
    }

    public void deleteById(Long id) {
        empleadoRepository.deleteById(id);
    }
}