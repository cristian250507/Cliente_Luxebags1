package Cliente_LuxeBags.ClienteLuxeBags.Assemblers;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import Cliente_LuxeBags.ClienteLuxeBags.Controller.ClienteControllerV2;
import Cliente_LuxeBags.ClienteLuxeBags.Model.Cliente;

@Component
public class ClientesModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>>{

    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
            linkTo(methodOn(ClienteControllerV2.class).getclienteByCodigo(cliente.getIdCliente())).withSelfRel(),
            linkTo(methodOn(ClienteControllerV2.class).getAllClientes()).withRel("clientes")
        );
    }
}




