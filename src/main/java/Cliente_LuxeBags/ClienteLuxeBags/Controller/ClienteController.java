package Cliente_LuxeBags.ClienteLuxeBags.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Cliente_LuxeBags.ClienteLuxeBags.Model.Cliente;
import Cliente_LuxeBags.ClienteLuxeBags.Service.ClienteService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;







@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteservice;

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteservice.listarClientes();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 Content
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/listar/ID/{idCliente}")
    public ResponseEntity<Cliente> ListarClientes(@PathVariable Integer idCliente) {
        Cliente cliente = clienteservice.buscarPorId(idCliente);
        if (cliente==null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/guardar/clientes")
    public ResponseEntity<String> guardarClientes(@RequestBody List<Cliente> clientes) {
        
        String mensaje = clienteservice.guardarClientes(clientes);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        
       
    }
    
    

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarCliente(@RequestBody Cliente cliente) {
        Cliente clienteExistente = clienteservice.buscarPorRut(cliente.getRut());
        if (clienteExistente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Ya existe un cliente con el RUT: " + cliente.getRut());
        }
        
        String mensaje = clienteservice.guardarCliente(cliente);
        if (mensaje.contains("Esa id de dirección ya fue agregada")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(mensaje);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarCliente(@RequestBody Cliente cliente) {
        Cliente clienteExtistente =  clienteservice.buscarPorRut(cliente.getRut());
        if (clienteExtistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente con Id"+ cliente.getIdCliente()+ "no encontrado");
        }
        clienteExtistente.setNombres(cliente.getNombres());
        clienteExtistente.setApellidos(cliente.getApellidos());
        clienteExtistente.setCorreoElectronico(cliente.getCorreoElectronico());
        clienteExtistente.setFechaNacimiento(cliente.getFechaNacimiento());
        clienteExtistente.setDireccion(cliente.getDireccion());
        String mensaje = clienteservice.actualizarCliente(clienteExtistente);
        return ResponseEntity.ok(mensaje);  // 200 OK con el mensaje de éxito
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Integer id) {
        Cliente clienteExistente = clienteservice.buscarPorId(id);
        if (clienteExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente con ID: " + id + "  no encontrado.");
        }

        String mensaje = clienteservice.eliminarCliente(id);
        return ResponseEntity.ok(mensaje);  // 200 OK con el mensaje de éxito
    }








    
    
}
