package com.unla.tp_oo2_g16.Configuration.Swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Nombre de tu API")
                        .version("1.0")
                        .description("Documentación de endpoints para el sistema de turnos")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}

//http://localhost:8080/swagger-ui/index.html#/