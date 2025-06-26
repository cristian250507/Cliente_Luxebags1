package Cliente_LuxeBags.ClienteLuxeBags.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Cliente_LuxeBags.ClienteLuxeBags.Model.Cliente;



@Repository
public interface ClienteRepository extends JpaRepository<Cliente ,Integer >{

    Cliente findByRut(String rut);

    @Query("SELECT c FROM Cliente c WHERE c.direccion.id_direccion = :idDireccion")
    Cliente findByDireccionId(@Param("idDireccion") String idDireccion);

    boolean existsByRut(String rut);

    

}
