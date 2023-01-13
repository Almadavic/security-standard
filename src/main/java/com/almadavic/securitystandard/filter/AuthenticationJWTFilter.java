package com.almadavic.securitystandard.filter;


import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.repository.UserRepository;
import com.almadavic.securitystandard.service.serviceAction.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@RequiredArgsConstructor
public class AuthenticationJWTFilter extends OncePerRequestFilter {

    // Quando a aplicação subir, essa classe vai ser chamada antes do spring security.
    // Nas proximas requisições , o spring security não vai ser chamado mais, pois as informações já serão salvas em memória,
    // porém, esse filtro sera chamado em todas as requisições para recuperar o token, validar se o token está válido...

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) // Método para prosseguir com a requisição!
            throws ServletException, IOException {
        String token = recoverToken(request);
        boolean valid = tokenService.isTokenValid(token); // retorna V/F se o token está valido
        if (valid) { // se o token estiver válido
            recoverUser(token); // autentica o usuário.
        }
        filterChain.doFilter(request, response);

    }


    private void recoverUser(String token) { // Método para recuperar o usuário e valida-lo através do token.

        String idUser = tokenService.getIdUser(token);
        Optional<User> userOptional = userRepository.findById(idUser);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

    }

    private String recoverToken(HttpServletRequest request) { // Método para recuperar o token
        String token = request.getHeader("Authorization");
        if (token == null  || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }

}
