package com.almadavic.securitystandard.config.securityConfig;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;


public interface SecurityConfigurations {

    PasswordEncoder encoder();    // Encoder de senha, codificar a senha.


    // Indica que o spring vai gerenciar a instancia do AuthenticationManager quando solicitado.
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception;


    // Configurações de autorização
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception;


    //Configuracoes de recursos estaticos(js, css, imagens, etc.)
    WebSecurityCustomizer webSecurityCustomizer();


    // Permite que o servidor seja acessado em ambiente de produção por um front-end que esteja hospedado em produção eu outra host.
    CorsConfigurationSource corsConfigurationSource();

}
