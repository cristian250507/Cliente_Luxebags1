package Cliente_LuxeBags.ClienteLuxeBags.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().
        info(new Info().
        title("API 2025 Gestion Clientes Luxebags").
        version("1.0").description("Documentacion de Api para el sistema de gestion de Clientes"));
    }

}
