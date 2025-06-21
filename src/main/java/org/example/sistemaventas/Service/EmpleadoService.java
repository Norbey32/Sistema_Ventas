package org.example.sistemaventas.Service;

import org.example.sistemaventas.Model.Empleado;
import org.example.sistemaventas.Repository.EmpleadoRepository;
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

    public Empleado update(Long id, Empleado empleado) {
        // Verificar si el empleado existe
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("Empleado no encontrado");
        }
        // Validar email único
        if (empleadoRepository.findByEmail(empleado.getEmail()) != null &&
                !empleadoRepository.findByEmail(empleado.getEmail()).getEmpleado_id().equals(id)) {
            throw new RuntimeException("El email ya está registrado");
        }
        empleado.setEmpleado_id(id);
        return empleadoRepository.save(empleado);
    }
    public void deleteById(Long id) {
        empleadoRepository.deleteById(id);
    }
}