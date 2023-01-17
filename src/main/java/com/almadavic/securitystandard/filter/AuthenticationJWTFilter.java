package com.almadavic.securitystandard.filter;


import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.repository.UserRepository;
import com.almadavic.securitystandard.service.serviceAction.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@AllArgsConstructor
public class AuthenticationJWTFilter extends OncePerRequestFilter {

    // Quando a aplicação subir, essa classe vai ser chamada antes do spring security.
    // Nas proximas requisições , o spring security não vai ser chamado mais, pois as informações já serão salvas em memória,
    // porém, esse filtro sera chamado em todas as requisições para recuperar o token, autenticar se tiver necessidade...

    private TokenService tokenService; // Dependencia tokenService
    private UserRepository userRepository; // Dependencia userRepository

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Método faz algumas ações, depois disso, o fluxo normal segue, a requisição continua para onde deveria.

        String tokenJWT = recoverToken(request); // É recuperado o token da request.

        if (tokenJWT != null) { // Se o token for diferente de null

            User user = recoverUser(tokenJWT);  // É recuperado o usuário  associado ao token

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); // Instancia com dados do usuario para autenticar.
            SecurityContextHolder.getContext().setAuthentication(authentication); // A autenticação é feita.

        }

        filterChain.doFilter(request, response); // Essa linha diz que a requisição irá continuar...
    }

    private User recoverUser(String tokenJWT) {  // Método  para recuperar o usuario logado
        String subject = tokenService.getSubject(tokenJWT); // É recuperado o sujeito do toke, no caso, o id
        User user = userRepository.findById(subject).get(); // Recupera o usuário relacionado com esse id
        return user;
    }

    private String recoverToken(HttpServletRequest request) { // Método recupera o token passado no header da request.
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

}