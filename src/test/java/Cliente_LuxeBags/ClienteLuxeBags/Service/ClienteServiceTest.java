package Cliente_LuxeBags.ClienteLuxeBags.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import Cliente_LuxeBags.ClienteLuxeBags.Model.Cliente;
import Cliente_LuxeBags.ClienteLuxeBags.Model.Direccion;
import Cliente_LuxeBags.ClienteLuxeBags.Model.HistorialDeCompra;
import Cliente_LuxeBags.ClienteLuxeBags.Repository.ClienteRepository;



@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;
    private Cliente cliente;
    
    @BeforeEach
    public void setUp() {
        cliente = new Cliente(
            1, 
            "11111111-1", 
            "Juan", 
            "Pizarro", 
            "25/05/05", 
            "juan@gmail.com", 
            "912345678", 
            new Direccion("DIR001", "Av. Siempre Viva 742", "Springfield", "12345", "EE.UU."),
            new HistorialDeCompra("HIST001", "2024-05-20", 45990.0)
        );
    }

    @Test
    public void testListarCliente(){
        Cliente cliente = new Cliente(
            1, 
            "11111111-1", 
            "Juan", 
            "Pizarro", 
            "25/05/05", 
            "juan@gmail.com", 
            "912345678", 
            new Direccion("DIR001", "Av. Siempre Viva 742", "Springfield","12345", "EE.UU."),
            new HistorialDeCompra("HIST001","2024-05-20",45990.0)
        );

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> clientes = clienteService.listarClientes();

        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals("Juan", clientes.get(0).getNombres());
    }


    @Test
    public void testGuardarCliente() {
        // Simula que no existe ningún cliente con esa dirección
        when(clienteRepository.findByDireccionId("DIR001")).thenReturn(null);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        String mensaje = clienteService.guardarCliente(cliente);

        assertEquals("El cliente con el siguiente ID: 1 fue guardado con exito", mensaje);
        verify(clienteRepository, times(1)).save(cliente);
    }

    
    @Test
    public void testActualizarCliente() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        String mensaje = clienteService.actualizarCliente(cliente);

        assertEquals("Cliente: 1 actualizado con exito", mensaje);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testEliminarCliente() {
        doNothing().when(clienteRepository).deleteById(1);

        String mensaje = clienteService.eliminarCliente(1);

        assertEquals("Cliente: 1 eliminado con exito", mensaje);
        verify(clienteRepository, times(1)).deleteById(1);
    }


    @Test
    public void testBuscarPorRut() {
        when(clienteRepository.findByRut("11111111-1")).thenReturn(cliente);

        Cliente encontrado = clienteService.buscarPorRut("11111111-1");
        assertNotNull(encontrado);
        assertEquals("Juan", encontrado.getNombres());
    }






}



