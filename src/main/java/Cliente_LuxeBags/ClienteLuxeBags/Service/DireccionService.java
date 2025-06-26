
package Cliente_LuxeBags.ClienteLuxeBags.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Cliente_LuxeBags.ClienteLuxeBags.Model.Direccion;

import Cliente_LuxeBags.ClienteLuxeBags.Repository.DireccionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DireccionService {


    @Autowired
    private DireccionRepository direccionRepository;



    public List<Direccion> listarDireccion(){
        return direccionRepository.findAll();
    }


    public String guardarDireccion(Direccion direccion){
        direccionRepository.save(direccion);
        return "Se guardo la direccion  con el siguiente ID: " + direccion.getId_direccion();
    }

    public Direccion buscarPorId(String id_direccion) {
        return direccionRepository.findById(id_direccion).orElse(null);
    }


    public String eliminarPorId(String id_direccion) {
        direccionRepository.deleteById(id_direccion);
        return "Se eliminó la dirección con el siguiente ID: " + id_direccion;
    }


    public String actualizarDireccion(Direccion direccion) {
        direccionRepository.save(direccion);
        return "Se actualizó la dirección con ID: " + direccion.getId_direccion();
    }

}
