package com.Badhwar.journalApp.config;

import io.swagger.v3.oas.annotations.tags.Tags;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customSwaggerConfig()
    {
        return new OpenAPI() // Info Regarding OpenAPI specs
                    .info( //General Info
                            new Info().title("Journal App APIs")
                                    .description("By Karan")
                    ).servers( //Info About Servers
                            List.of(
                                    new Server().url("http://localhost:8080").description("Local"),
                                    new Server().url("http://localhost:8081").description("Live")
                                    )
                     ).tags(// Info ABout Tags, Custom tags we gave
                        List.of(
                                new Tag().name("Public APIs"),
                                new Tag().name("User APIs"),
                                new Tag().name("Journal APIs"),
                                new Tag().name("Admin APIs")
                        )
                    )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));
    }
}
