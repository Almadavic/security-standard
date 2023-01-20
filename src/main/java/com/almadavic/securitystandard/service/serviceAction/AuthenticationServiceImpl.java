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
@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@Primary // Essa vai ser a implementação a ser carregada caso tenha mais de 1.
public class AuthenticationServiceImpl implements AuthenticationService { // Serviço relacionado a autenticação do usuário no sistema.

    private final TokenService tokenService; // injeção de dependencia do TokenService -> gerar o token.

    private final AuthenticationManager authManager;    // Injeção de dependencia automatica - > AuthenticationManager

    @Override
    public Token authenticate(LoginDTO loginData) { // Método para fazer o login e se autenticar no sistema.

        UsernamePasswordAuthenticationToken login = loginData.toConvert();   // converte os dados passado pelo usuário em um token de autenticação

        try {
            Authentication authentication = authManager.authenticate(login); // autentica o usuário com base nos dados informados por ele

            String token = tokenService.generateToken(authentication);   // geração do token, e esse token é devolvido para o controller para ser enviado ao client e
                                                                                                                   // ser usado nas próximas requisições.

            return new Token(token, "Bearer");

        } catch (AuthenticationException e) {
            throw new DatabaseException("E-mail and / or password is / are wrong!");       // Causará um erro caso os dados passados pelo usuário estejam errados.
        }

    }

}
