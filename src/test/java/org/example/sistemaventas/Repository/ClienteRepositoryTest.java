package org.example.sistemaventas.Repository;

import org.example.sistemaventas.Model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void whenFindByEmail_thenReturnCliente() {
        // Given: Preparar el objeto con los datos requeridos.
        Cliente cliente = new Cliente();
        cliente.setNombre("Test");
        cliente.setApellido("User"); // Se añade el apellido para que no sea nulo.
        cliente.setEmail("test@test.com");
        clienteRepository.save(cliente);

        // When: Ejecutar el método a probar.
        Cliente found = clienteRepository.findByEmail("test@test.com");

        // Then: Verificar el resultado.
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    void whenFindByEmail_thenReturnsNullForNonExisting() {
        // When: No se guarda ningún cliente.
        Cliente found = clienteRepository.findByEmail("nonexistent@test.com");

        // Then: El resultado debe ser nulo.
        assertThat(found).isNull();
    }

    @Test
    void whenFindAll_thenReturnAllClients() {
        // Given: Se guardan dos clientes con todos los campos requeridos.
        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Juan");
        cliente1.setApellido("Perez");
        cliente1.setEmail("juan@example.com");
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Ana");
        cliente2.setApellido("Gomez");
        cliente2.setEmail("ana@example.com");
        clienteRepository.save(cliente2);

        // When: Se obtienen todos los clientes.
        List<Cliente> clients = clienteRepository.findAll();

        // Then: Se verifica que la lista tiene el tamaño esperado.
        assertThat(clients).hasSize(2);
    }
}