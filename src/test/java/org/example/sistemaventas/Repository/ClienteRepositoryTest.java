package org.example.sistemaventas.Repository;

import org.example.sistemaventas.Model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void whenFindByEmail_thenReturnCliente() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNombre("Test");
        cliente.setEmail("test@test.com");
        clienteRepository.save(cliente);

        // When
        Cliente found = clienteRepository.findByEmail("test@test.com");

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    void whenFindByEmail_thenReturnsNullForNonExisting() {
        // When
        Cliente found = clienteRepository.findByEmail("nonexistent@test.com");

        // Then
        assertThat(found).isNull();
    }

    @Test
    void whenFindAll_thenReturnAllClients() {
        // Given
        clienteRepository.save(new Cliente());
        clienteRepository.save(new Cliente());

        // When
        var clients = clienteRepository.findAll();

        // Then
        assertThat(clients).hasSize(2);
    }
}