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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@EnableWebSecurity
// Desabilita as configurações default do Spring Security, permitindo o desenvolvedor configurar as próprias configurações.
@Configuration  // Indica que é uma classe de configuração.
@Primary // Essa vai ser a implementação a ser carregada caso tenha mais de 1.
public class SecurityConfigurationsImpl implements SecurityConfigurations { // As classes de Security só são chamadas quando a aplicação sobe!
    // Nas proximas requisições, essa classe não é chamada novamente, pois as configurações já estão salvas em memória.

    private final TokenService tokenService; // Classe que contém ações relacionadas á um token: gerar um token, validar um token, recuperar o subject do token...

    private final UserRepository userRepository; // Repositório da entidade Usuário.

    @Override
    @Bean
    public PasswordEncoder encoder() { // Encoder de senha, codifica a senha.
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {  // Autentica o usuário.
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Configurações de Autorização.

        http.authorizeHttpRequests() // Autorização de requests.
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() // Permitindo os recursos do swagger. (Todos podem acessar).
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll() // Permitindo os recursos do H2. (Todos podem acessar).

                .requestMatchers("/auth", "/users/register").permitAll() // Todos podem acessar esses recursos.
                .requestMatchers("/users", "/users/**").hasRole("ADMIN") // Apenas ADMINISTRADORES podem acessar esses recursos.
                .anyRequest().authenticated() // Qualquer outro recurso, sem ser os de cima, poderão ser acessados apenas se estiver autenticado.
                .and().cors() // Libera a integração de aplicações externas como o front-end á essa API.
                .and().headers().frameOptions().disable() // É para bloquear a página de login ser colocada em um iFrame.
                .and().csrf().disable() // Comentário sobre essa configuração na linha 89.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // DIZ QUE A APLICAÇÃO NÃO TEM ESTADO, OS DADOS SÃO GUARDADOS EM UM TOKEN!
                .and().addFilterBefore(new AuthenticationJWTFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class) // Adiciona um FILTRO antes da classe atual ser chamada.
                .exceptionHandling().authenticationEntryPoint(new UnauthorizedHandler()) // Essa linha vai chamar a classe Unhautorize... para lídar com o erro 401.
                .and().exceptionHandling().accessDeniedHandler(new ForbiddenHandler());  // Essa linha chama a classe Forbidden para lídar com o erro 403.

        return http.build();
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


