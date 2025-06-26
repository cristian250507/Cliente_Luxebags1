package Cliente_LuxeBags.ClienteLuxeBags.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Cliente_LuxeBags.ClienteLuxeBags.Model.Cliente;
import Cliente_LuxeBags.ClienteLuxeBags.Model.Direccion;
import Cliente_LuxeBags.ClienteLuxeBags.Service.DireccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;




@RestController
@RequestMapping("/direccion")
@Tag(name = "Direccion", description = "Operaciones relacionadas con direccion")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping("/listar")
    @Operation(
        summary = "Listar todas las direcciones",
        description = "Obtiene una lista de todas las direcciones registradas en el sistema.")
    public ResponseEntity<List<Direccion>> listarDirecciones() {
        List<Direccion> direcciones = direccionService.listarDireccion();
        return ResponseEntity.ok(direcciones);
    }

    @PostMapping("/guardar")
    @Operation(
        summary = "Guardar nueva dirección",
        description = "Guarda una nueva dirección en la base de datos. Si el ID ya existe, retorna un error 400.")
    public ResponseEntity<String> guardarDireccion(@RequestBody Direccion direccion) {
        Direccion direccionExiste = direccionService.buscarPorId(direccion.getId_direccion());
        if (direccionExiste != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("El ID proporcionado ya existe en la base de datos.");
        }

        String mensaje = direccionService.guardarDireccion(direccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(
        summary = "Eliminar dirección por ID",
        description = "Elimina una dirección en base a su ID. Si no se encuentra, retorna 404. Si se elimina correctamente, retorna un mensaje con código 200.")
    public ResponseEntity<String> eliminarDireccion(@PathVariable("id") String id) {
        Direccion direccion = direccionService.buscarPorId(id);
        if (direccion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Dirección con ID: " + id + " no encontrada.");
        }

        String mensaje = direccionService.eliminarPorId(id);
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/actualizar")
    @Operation(
        summary = "Actualizar dirección existente",
        description = "Actualiza los datos de una dirección existente. Si no se encuentra el ID, retorna un error 404.")
    public ResponseEntity<String> actualizarDireccion(@RequestBody Direccion direccion) {
        Direccion direccionExistente = direccionService.buscarPorId(direccion.getId_direccion());
        if (direccionExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Dirección con ID: " + direccion.getId_direccion() + " no encontrada.");
        }

        direccionExistente.setCalle(direccion.getCalle());
        direccionExistente.setCiudad(direccion.getCiudad());
        direccionExistente.setCodigoPostal(direccion.getCodigoPostal());
        direccionExistente.setPais(direccion.getPais());

        String mensaje = direccionService.actualizarDireccion(direccionExistente);
        return ResponseEntity.ok(mensaje);
    }




}



