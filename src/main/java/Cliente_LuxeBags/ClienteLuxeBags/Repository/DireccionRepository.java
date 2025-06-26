package Cliente_LuxeBags.ClienteLuxeBags.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Cliente_LuxeBags.ClienteLuxeBags.Model.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, String>{

}
