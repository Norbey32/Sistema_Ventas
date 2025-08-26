package org.example.sistemaventas.Service;

import org.example.sistemaventas.Model.Cliente;
import org.example.sistemaventas.Repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void whenFindAll_thenReturnsListOfClients() {
        // Given
        Cliente c1 = new Cliente();
        Cliente c2 = new Cliente();
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        // When
        var clients = clienteService.findAll();

        // Then
        assertNotNull(clients);
        assertEquals(2, clients.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void whenSaveClienteWithUniqueEmail_thenReturnsClient() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setEmail("test@test.com");
        when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(null);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // When
        Cliente savedClient = clienteService.save(cliente);

        // Then
        assertNotNull(savedClient);
        verify(clienteRepository, times(1)).findByEmail(cliente.getEmail());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void whenSaveClienteWithExistingEmail_thenThrowsException() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setEmail("test@test.com");
        when(clienteRepository.findByEmail(cliente.getEmail())).thenReturn(new Cliente());

        // When/Then
        assertThrows(RuntimeException.class, () -> clienteService.save(cliente));
        verify(clienteRepository, times(1)).findByEmail(cliente.getEmail());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}