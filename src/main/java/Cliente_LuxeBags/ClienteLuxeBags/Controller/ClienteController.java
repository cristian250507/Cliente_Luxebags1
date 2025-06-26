package Cliente_LuxeBags.ClienteLuxeBags.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Cliente_LuxeBags.ClienteLuxeBags.Model.Cliente;
import Cliente_LuxeBags.ClienteLuxeBags.Service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;







@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Operaciones relacionadas con clientes")
public class ClienteController {
    

    @Autowired
    private ClienteService clienteservice;

    @GetMapping("/listar")
    @Operation(summary = "Obtiene todos lo clientes", description = "Obtienes una lista de todos los clientes")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteservice.listarClientes();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/listar/ID/{idCliente}")
    public ResponseEntity<Cliente> listarClientePorId(@PathVariable Integer idCliente) {
        Cliente cliente = clienteservice.buscarPorId(idCliente);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // ✅ ahora sí retorna
        }
        return ResponseEntity.ok(cliente);
    }


    @PostMapping("/guardar/clientes")
    @Operation(
    summary = "Guardar múltiples clientes",
    description = "Permite guardar una lista de clientes nuevos.Retorna 201 si se guardan correctamente o 400 si hay errores de validación.")
    public ResponseEntity<String> guardarClientes(@RequestBody List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            if (cliente.getNombres().isEmpty() ||
                cliente.getApellidos().isEmpty() ||
                cliente.getCorreoElectronico().isEmpty() ||
                cliente.getFechaNacimiento().isEmpty() ||
                cliente.getTelefono().isEmpty() ||
                cliente.getRut().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Todos los campos son obligatorios.");
            }
            Cliente clienteExistente = clienteservice.buscarPorRut(cliente.getRut());
            if (clienteExistente != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ya existe un cliente con el RUT: " + cliente.getRut());
            }
        }
        String mensaje = clienteservice.guardarClientes(clientes);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }
    
    

    @PostMapping("/guardar")
    @Operation(
    summary = "Guardar un cliente",
    description = "Guarda un nuevo cliente. Retorna 201 si se guarda exitosamente o 400 si hay errores.")
    public ResponseEntity<String> guardarCliente(@RequestBody Cliente cliente) {
        if (cliente.getNombres().isEmpty() ||
            cliente.getApellidos().isEmpty() ||
            cliente.getCorreoElectronico().isEmpty() ||
            cliente.getFechaNacimiento().isEmpty() ||
            cliente.getTelefono().isEmpty() ||
            cliente.getRut().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Todos los campos son obligatorios.");
        }

        Cliente clienteExistente = clienteservice.buscarPorRut(cliente.getRut());
        if (clienteExistente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Ya existe un cliente con el RUT: " + cliente.getRut());
        }
    
        String mensaje = clienteservice.guardarCliente(cliente);
        if (mensaje.contains("Esa id de dirección ya fue agregada a otro cliente")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(mensaje);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @PutMapping("/actualizar")
    @Operation(
    summary = "Actualizar cliente",
    description = "Actualiza los datos de un cliente existente identificado por su RUT")
    public ResponseEntity<String> actualizarCliente(@RequestBody Cliente cliente) {
        Cliente clienteExtistente =  clienteservice.buscarPorRut(cliente.getRut());
        if (clienteExtistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente con rut no encontrado");
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
    @Operation(
    summary = "Eliminar cliente por ID",
    description = "Elimina un cliente existente en base a su ID. Si no se encuentra, retorna 404. Si se elimina correctamente, retorna un mensaje con código 200.")

    public ResponseEntity<String> eliminarCliente(@PathVariable Integer id) {
        Cliente clienteExistente = clienteservice.buscarPorId(id);
        if (clienteExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente con ID: " + id + "  no encontrado.");
        }

        String mensaje = clienteservice.eliminarCliente(id);
        return ResponseEntity.ok(mensaje); 
    }








    
    
}
