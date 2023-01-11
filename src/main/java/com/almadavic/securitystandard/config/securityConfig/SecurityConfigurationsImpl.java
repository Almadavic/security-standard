package com.almadavic.securitystandard.config.securityConfig;

import com.almadavic.securitystandard.config.exceptionConfig.handler.ForbiddenHandler;
import com.almadavic.securitystandard.config.exceptionConfig.handler.UnauthorizedHandler;
import com.almadavic.securitystandard.filter.AuthenticationJWTFilter;
import com.almadavic.securitystandard.repository.UserRepository;
import com.almadavic.securitystandard.service.serviceAction.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@EnableWebSecurity
// Desabilita as configurações default do Spring Security, permitindo a gente á configurar as nossas próprias.
@Configuration  // Indica que é uma classe de configuração
@Primary // Essa vai ser a implementação principal a ser carregada.
public class SecurityConfigurationsImpl implements SecurityConfigurations { // As classes de Security só são chamadas quando a aplicação sobe!
    // Nas proximas requisições, essa classe não é chamada dnv, pois as conf já estão salvas.

    private final TokenService tokenService; // Classe que contém ações de um token como gerar um token...
    private final UserRepository userRepository; // Repositório da entidade Usuário


    @Override
    @Bean
    public PasswordEncoder encoder() { // Encoder de senha, codificar a senha.
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {  // AuthManager, Conf de autenticação.
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Configs de Autorização.

        http.authorizeRequests()        // Autorização de requests
                .antMatchers("/auth").permitAll() // Estou permitindo TUDO E TODOS acessarem esse recurso no sistema
                .antMatchers("/users/register").permitAll() // Estou permitindo TUDO E TODOS acessarem esse recurso no sistema
                .antMatchers("/users").hasRole("ADMIN") // Para acessar esse recurso, o usuario precisa estar logado e tem que ser ADM
                .antMatchers("/users/**").hasRole("ADMIN") // Para acessar esse recurso, o usuario precisa estar logado e tem que ser ADM
                .anyRequest().authenticated() // Qualquer outro recurso, sem ser os de cima, poderão ser acessados apenas se estiver autenticado.
                .and().cors() // Libera a integração de aplicações externas como o front-end á essa API.
                .and().headers().frameOptions().disable() // É para bloquear a página de login ser colocada em um iFrame
                .and().csrf().disable() // Comentário sobre essa conf na linha 92.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // DIZ QUE A APLICAÇÃO NÃO TEM ESTADO, OS DADOS SÃO GUARDADOS EM UM TOKEN!
                .and().addFilterBefore(new AuthenticationJWTFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class) // Adiciona um FILTRO, Antes
                .exceptionHandling().authenticationEntryPoint(new UnauthorizedHandler()) // Essa linha vai chamar a classe Unhautorize... para lídar com o erro 401.
                .and().exceptionHandling().accessDeniedHandler(new ForbiddenHandler());    // Essa linha chama a classe Forbidden para lídar com o erro 403.


        return http.build();
    }

    @Override
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { //Configuracoes de recursos estaticos(js, css, imagens, etc.)
        return (web) -> web.ignoring().antMatchers
                ("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**");
    }

    @Override
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {           // Método relacionado á CORS, integração com um meio externo.
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


     /*
         csrf - é uma proteção contra hackers que "roubam a sessão", como estamos usando staless, n temos sessão, e deixar essa conf(padrão habilitada)
         não teria sentido, pois é um cenário impossivel, por isso desativamos.
         */


}


