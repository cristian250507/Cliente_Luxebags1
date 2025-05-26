package Cliente_LuxeBags.ClienteLuxeBags.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import Cliente_LuxeBags.ClienteLuxeBags.Model.HistorialDeCompra;
import Cliente_LuxeBags.ClienteLuxeBags.Repository.HistorialDeCompraController;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class HistorialDeCompraService {

    @Autowired

    private HistorialDeCompraController historialdecompracontroller;


    public List<HistorialDeCompra> listarHistorial(){
        return historialdecompracontroller.findAll();
    }

    public String guardarHistorial(HistorialDeCompra historialDeCompra){
        historialdecompracontroller.save(historialDeCompra);
        return "Se guardo el historial de compra con el siguiente ID: " + historialDeCompra.getId_historial();
    }

    public HistorialDeCompra buscarporId(String id_historial){
        return historialdecompracontroller.findById(id_historial).orElse(null);
    }

    public String eliminarPorID(String id_historial){
         historialdecompracontroller.deleteById(id_historial);
        return "Se elimino el Historial con el siguiente ID: " + id_historial;
    }
}
