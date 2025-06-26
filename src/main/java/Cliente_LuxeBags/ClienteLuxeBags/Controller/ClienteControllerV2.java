package Cliente_LuxeBags.ClienteLuxeBags.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web.Client;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import Cliente_LuxeBags.ClienteLuxeBags.Assemblers.ClientesModelAssembler;
import Cliente_LuxeBags.ClienteLuxeBags.Model.Cliente;
import Cliente_LuxeBags.ClienteLuxeBags.Service.ClienteService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes/V2/")
@Tag(name = "Clientes Version 2")
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClientesModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Cliente>> getAllClientes() {
        List<EntityModel<Cliente>> clientes = clienteService.listarClientes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(clientes,
                linkTo(methodOn(ClienteControllerV2.class).getAllClientes()).withSelfRel());
    }

    @GetMapping(value = "/{ID}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Cliente> getclienteByCodigo(@PathVariable Integer ID) {
        Cliente cliente = clienteService.buscarPorId(ID);
        return assembler.toModel(cliente);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        String resultado = clienteService.guardarCliente(cliente);

        if (resultado.contains("Esa id de dirección ya fue agregada")) {
            return ResponseEntity.badRequest().body(resultado);
        }

        // Recuperar el cliente guardado desde la base de datos
        Cliente guardado = clienteService.buscarPorRut(cliente.getRut());

        return ResponseEntity
                .created(linkTo(methodOn(ClienteControllerV2.class).getclienteByCodigo(guardado.getIdCliente())).toUri())
                .body(assembler.toModel(guardado));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        cliente.setIdCliente(id);
        String resultado = clienteService.actualizarCliente(cliente);

        Cliente actualizado = clienteService.buscarPorId(id);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado tras la actualización.");
        }

        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteCarrera(@PathVariable Integer id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }



}
