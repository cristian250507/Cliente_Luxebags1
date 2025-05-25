package Cliente_LuxeBags.ClienteLuxeBags.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(unique = true, length = 13, nullable = false)
    private String rut;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private String fechaNacimiento;

    @Column(nullable = false)
    private String correoElectronico;

    @Column(nullable = false)
    private String telefono;

    @OneToOne
    @JoinColumn(name = "id_direccion", nullable = false)
    private Direccion direccion;

    @ManyToOne
    @JoinColumn(name = "id_historial", nullable = false)
    private HistorialDeCompra historialdecompra;
    




}
