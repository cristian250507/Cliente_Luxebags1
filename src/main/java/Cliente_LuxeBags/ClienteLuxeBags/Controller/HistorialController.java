package Cliente_LuxeBags.ClienteLuxeBags.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Cliente_LuxeBags.ClienteLuxeBags.Model.Cliente;
import Cliente_LuxeBags.ClienteLuxeBags.Model.HistorialDeCompra;
import Cliente_LuxeBags.ClienteLuxeBags.Service.HistorialDeCompraService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/historial")
public class HistorialController {

    @Autowired
    private HistorialDeCompraService historialDeCompraService;

    @GetMapping("/listar")
    public ResponseEntity<List<HistorialDeCompra>> listarHistorial() {
        List<HistorialDeCompra> historial = historialDeCompraService.listarHistorial();


        return ResponseEntity.ok(historial);
    }
    

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarHistorial(@RequestBody HistorialDeCompra historial) {
        HistorialDeCompra historialExiste = historialDeCompraService.buscarporId(historial.getId_historial());
        if (historialExiste != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID proporcionado ya existe en la base de datos.");
        }

        String mensaje = historialDeCompraService.guardarHistorial(historial);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable("id") String id) {
        HistorialDeCompra historialDeCompra = historialDeCompraService.buscarporId(id);
        if (historialDeCompra == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta con ID: " + id + " no encontrado.");
        }

        String mensaje = historialDeCompraService.eliminarPorID(id);
        return ResponseEntity.ok(mensaje);  // 200 OK con el mensaje de éxito
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarHistorial(@RequestBody HistorialDeCompra historialdecompra) {
        HistorialDeCompra historialdecompraExiste = historialDeCompraService.buscarporId(historialdecompra.getId_historial());
        if (historialdecompraExiste == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Historial con id: "+ historialdecompra.getId_historial()+ "no encontrado");
        }
        historialdecompraExiste.setFecha_compra(historialdecompra.getFecha_compra());
        historialdecompraExiste.setMonto(historialdecompra.getMonto());
        String mensaje = historialDeCompraService.actualizarhistorial(historialdecompraExiste);
        return ResponseEntity.ok(mensaje);  // 200 OK con el mensaje de éxito
    }





    
}
