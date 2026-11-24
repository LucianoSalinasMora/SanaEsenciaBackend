package com.sanaesencia.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Centro Integrativo Sanaesencia")
                        .version("1.0.0")
                        .description("Microservicios para la gestión de turnos, citas médicas, boxes (Los Vilos) y productos terapéuticos.")
                        .contact(new Contact()
                                .name("Centro Integrativo Sanaesencia")
                                .email("contacto@centrosanaesencia.cl")
                                .url("https://centrosanaesencia.cl"))
                        .license(new License().name("Apache 2.0")));
    }
}
