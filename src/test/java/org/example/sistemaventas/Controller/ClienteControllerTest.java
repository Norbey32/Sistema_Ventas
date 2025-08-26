package org.example.sistemaventas.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sistemaventas.Model.Cliente;
import org.example.sistemaventas.Service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenListarClientes_thenReturnOkAndListOfClients() throws Exception {
        // Given
        Cliente c1 = new Cliente();
        c1.setId(1L);
        Cliente c2 = new Cliente();
        c2.setId(2L);
        when(clienteService.findAll()).thenReturn(Arrays.asList(c1, c2));

        // When/Then
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void whenCrearCliente_thenReturnOkAndClient() throws Exception {
        // Given
        Cliente newClient = new Cliente();
        newClient.setNombre("Nuevo");
        when(clienteService.save(any(Cliente.class))).thenReturn(newClient);

        // When/Then
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newClient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nuevo"));
    }

    @Test
    void whenDeleteCliente_thenReturnNoContent() throws Exception {
        // Given
        doNothing().when(clienteService).deleteById(anyLong());

        // When/Then
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).deleteById(1L);
    }
}