       package Cliente_LuxeBags.ClienteLuxeBags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;
import Cliente_LuxeBags.ClienteLuxeBags.Controller.DireccionController;
import Cliente_LuxeBags.ClienteLuxeBags.Model.Cliente;
import Cliente_LuxeBags.ClienteLuxeBags.Model.Direccion;
import Cliente_LuxeBags.ClienteLuxeBags.Model.HistorialDeCompra;
import Cliente_LuxeBags.ClienteLuxeBags.Repository.ClienteRepository;
import Cliente_LuxeBags.ClienteLuxeBags.Repository.DireccionRepository;
import Cliente_LuxeBags.ClienteLuxeBags.Repository.HistorialDeCompraRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.text.SimpleDateFormat;


@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private ClienteRepository clienterepository;

    @Autowired
    private HistorialDeCompraRepository historialdecomprarepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //Generar historiales
        for (int i = 0 ; i<5; i++) {
            HistorialDeCompra historialDeCompra =  new HistorialDeCompra();
            historialDeCompra.setId_historial(UUID.randomUUID().toString());

            
            java.util.Date fechaUtil = faker.date().between(
            java.sql.Date.valueOf("2023-01-01"),
            java.sql.Date.valueOf("2025-01-01"));

            // Formatear la fecha a String yyyy-MM-dd
            String fechaString = sdf.format(fechaUtil);
            historialDeCompra.setFecha_compra(fechaString);

            historialDeCompra.setMonto(faker.number().randomDouble(2, 10, 500));

            historialdecomprarepository.save(historialDeCompra);
        }

        for (int i =0; i<5; i++){
            Direccion direccion = new Direccion();
            direccion.setId_direccion(UUID.randomUUID().toString());

            // Generar datos falsos para la dirección
            direccion.setCalle(faker.address().streetAddress());
            direccion.setCiudad(faker.address().city());
            direccion.setCodigoPostal(faker.address().zipCode());
            direccion.setPais(faker.address().country());

            direccionRepository.save(direccion);
        }


        List<Direccion> direcciones = direccionRepository.findAll();
        List<HistorialDeCompra> historiales = historialdecomprarepository.findAll();

        for (int i = 0; i < 5; i++) {


            Cliente cliente = new Cliente();
            String rut;
            do {
                rut = "RUT" + UUID.randomUUID().toString().substring(0, 8);
            } while (clienterepository.existsByRut(rut)); 
            cliente.setRut(rut);
            cliente.setNombres(faker.name().firstName());
            cliente.setApellidos(faker.name().lastName());
            java.util.Date fechaNacimiento = new java.util.Date(faker.date().birthday(18, 65).getTime());
            String fechaNacStr = new SimpleDateFormat("yyyy-MM-dd").format(fechaNacimiento);
            cliente.setFechaNacimiento(fechaNacStr);
            cliente.setCorreoElectronico(faker.internet().emailAddress());
            cliente.setTelefono(faker.phoneNumber().cellPhone());
            cliente.setHistorialdecompra(historiales.get(i));
            cliente.setDireccion(direcciones.get(i));

            clienterepository.save(cliente);
        }







    }

}
