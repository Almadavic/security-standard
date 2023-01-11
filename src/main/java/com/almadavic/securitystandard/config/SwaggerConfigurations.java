package com.almadavic.securitystandard.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfigurations { // Configurações do SWAGGER !

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("SecuritySkeletonAPI")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI almadaAPI() { // Nesse método estão algumas configurações que apareceram na interface do swagger e confs de autorização.
        return new OpenAPI()
                .info(new Info().title("SecuritySkeletonAPI")
                        .description("My standard project for authentication and authorization.")
                        .version("v0.0.1")
                        .license(new License().name("Spring Doc").url("http://springdoc.org")))
                .components(new Components().addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                .externalDocs(new ExternalDocumentation());
    }


}

