package com.almadavic.securitystandard.config.securityConfig;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;


public interface SecurityConfigurations { // Interface SecurityConfigurations

    PasswordEncoder encoder();    // Encoder de senha, codifica a senha.

    // Autentica o usuário no sistema.
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception;

    // Configurações de autorização
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception;

    // Permite que o servidor seja consumido pelo front-end.
    CorsConfigurationSource corsConfigurationSource();

}
