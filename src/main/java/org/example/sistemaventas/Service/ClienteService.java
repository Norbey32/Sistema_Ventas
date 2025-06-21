package org.example.sistemaventas.Service;

import org.example.sistemaventas.Model.Cliente;
import org.example.sistemaventas.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente) {
        // Validación: Email único
        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente update(Long id, Cliente cliente) {
        Optional<Cliente> existingClienteOpt = clienteRepository.findById(id);
        if (existingClienteOpt.isPresent()) {
            Cliente existingCliente = existingClienteOpt.get();
            existingCliente.setNombre(cliente.getNombre());
            existingCliente.setEmail(cliente.getEmail());
            existingCliente.setTelefono(cliente.getTelefono());
            return clienteRepository.save(existingCliente);
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
    }
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
}