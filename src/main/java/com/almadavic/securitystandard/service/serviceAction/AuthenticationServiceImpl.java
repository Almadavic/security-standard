package com.almadavic.securitystandard.service.serviceAction;


import com.almadavic.securitystandard.config.securityConfig.Token;
import com.almadavic.securitystandard.dto.request.LoginDTO;
import com.almadavic.securitystandard.service.customException.DatabaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service // Indica que é uma camada de serviço , o spring vai gerenciar automaticamente.
@RequiredArgsConstructor // Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@Primary // Essa vai ser a implementação principal a ser carregada.
public class AuthenticationServiceImpl implements AuthenticationService { // Serviço relacionado a autenticação do usuário no sistema.

    private final TokenService tokenService; // injeção de dependencia do TokenService -> gerar o token.

    @Override
    public Token authenticate(LoginDTO loginData, AuthenticationManager authManager) { // Método para fazer o login e se autenticar no sistema.

        UsernamePasswordAuthenticationToken login = loginData.toConvert();   // converter os dados passado pelo usuario em um token de autenticação

        try {
            Authentication authentication = authManager.authenticate(login); // autenticar usuário com base nos dados informados por ele

            String token = tokenService.generateToken(authentication);   // geração do token, e esse token é devolvido para o controller para ser enviado ao client.

            return new Token(token,"Bearer");

        } catch (AuthenticationException e) {
            throw new DatabaseException("E-mail and / or password is / are wrong!");       // Causará um erro caso os dados passados pelo usuário estejam errados.
        }
    }
}
