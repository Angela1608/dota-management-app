package com.dota.hero.manager.controller.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI heroManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dota Hero Manager API")
                        .description("REST API for managing Dota heroes, items, and match lobbies")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Development team")
                                .email("support@dotamanager.com")));
    }

}
