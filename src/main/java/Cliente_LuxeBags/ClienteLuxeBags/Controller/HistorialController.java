package Cliente_LuxeBags.ClienteLuxeBags.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Cliente_LuxeBags.ClienteLuxeBags.Model.HistorialDeCompra;
import Cliente_LuxeBags.ClienteLuxeBags.Service.HistorialDeCompraService;
import org.springframework.web.bind.annotation.GetMapping;

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
    
}
