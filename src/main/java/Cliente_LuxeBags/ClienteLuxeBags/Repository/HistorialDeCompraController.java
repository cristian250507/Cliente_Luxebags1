package Cliente_LuxeBags.ClienteLuxeBags.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Cliente_LuxeBags.ClienteLuxeBags.Model.HistorialDeCompra;

@Repository
public interface HistorialDeCompraController extends JpaRepository<HistorialDeCompra, String > {


    
}
