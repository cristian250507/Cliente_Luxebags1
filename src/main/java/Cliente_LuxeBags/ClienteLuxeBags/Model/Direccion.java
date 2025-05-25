package Cliente_LuxeBags.ClienteLuxeBags.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "direccion")
public class Direccion {

    @Id
    @Column(unique = true)
    private String id_direccion;

    @Column(nullable = false)
    private String calle;
    
    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String codigoPostal;

    @Column(nullable = false)
    private String pais;




}
