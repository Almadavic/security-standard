package com.almadavic.securitystandard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
// Com essa anotação, habilitamos esse suporte para o Spring pegar da requisição dos parâmetros da url, os campos, as informações de paginação e ordenação.
@EnableCaching // Habilita o cache!
public class SecurityStandardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityStandardApplication.class, args);
    }

}
