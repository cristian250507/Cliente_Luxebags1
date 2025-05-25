package Cliente_LuxeBags.ClienteLuxeBags.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "historialdecompra")
public class HistorialDeCompra {

    @Id
    @Column(unique = true)
    private String id_historial;

    @Column(nullable = false)
    private String fecha_compra;

    @Column(nullable = false)
    private Double monto;

    
    



}
