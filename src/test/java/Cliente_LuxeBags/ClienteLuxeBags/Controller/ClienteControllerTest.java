package Cliente_LuxeBags.ClienteLuxeBags.Controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import Cliente_LuxeBags.ClienteLuxeBags.Model.Cliente;
import Cliente_LuxeBags.ClienteLuxeBags.Model.Direccion;
import Cliente_LuxeBags.ClienteLuxeBags.Model.HistorialDeCompra;
import Cliente_LuxeBags.ClienteLuxeBags.Service.ClienteService;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        Direccion direccion = new Direccion("DIR001", "Av. Siempre Viva 742", "Springfield", "12345", "EE.UU.");
        HistorialDeCompra historial = new HistorialDeCompra("HIST001", "2024-05-20", 45990.0);

        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setRut("11111111-1");
        cliente.setNombres("Juan");
        cliente.setApellidos("Pizarro");
        cliente.setFechaNacimiento("25/05/05");
        cliente.setCorreoElectronico("juan@gmail.com");
        cliente.setTelefono("912345678");
        cliente.setDireccion(direccion);
        cliente.setHistorialdecompra(historial);
    }


    @Test
    public void testListarClientes() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setRut("12345678-9");
        cliente.setNombres("Pedro");
        cliente.setApellidos("González");

        when(clienteService.listarClientes()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/cliente/listar"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].rut").value("12345678-9"))
               .andExpect(jsonPath("$[0].nombres").value("Pedro"));
    }


    @Test
    public void testGuardarCliente() throws Exception {
        when(clienteService.guardarCliente(any(Cliente.class)))
            .thenReturn("Cliente 11111111-1 guardado con éxito");


        mockMvc.perform(post("/cliente/guardar")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(objectMapper.writeValueAsString(cliente)))  
            .andExpect(status().isCreated())  
            .andExpect(content().string("Cliente 11111111-1 guardado con éxito"));  
    }


    @Test
    public void testBuscarClientePorID() throws Exception {
        when(clienteService.buscarPorId(1)).thenReturn(cliente);

        mockMvc.perform(get("/cliente/listar/ID/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombres").value("Juan"))
            .andExpect(jsonPath("$.rut").value("11111111-1"));
    }

    @Test
    public void testEliminarCliente() throws Exception {
        when(clienteService.buscarPorId(1)).thenReturn(cliente);
        when(clienteService.eliminarCliente(1)).thenReturn("Cliente 1 eliminado con éxito");

        mockMvc.perform(delete("/cliente/eliminar/1"))
            .andExpect(status().isOk())  
            .andExpect(content().string("Cliente 1 eliminado con éxito"));  
    }


    @Test
    public void testActualizarCliente() throws Exception {
        when(clienteService.buscarPorRut("11111111-1")).thenReturn(cliente);
    
        when(clienteService.actualizarCliente(any(Cliente.class))).thenReturn("Cliente actualizado");


        cliente.setCorreoElectronico("nuevo@email.com");

 
        mockMvc.perform(put("/cliente/actualizar")
                        .contentType(MediaType.APPLICATION_JSON) 
                        .content(objectMapper.writeValueAsString(cliente)))  
                .andExpect(status().isOk())  
                .andExpect(content().string("Cliente actualizado"));  
    }


}









