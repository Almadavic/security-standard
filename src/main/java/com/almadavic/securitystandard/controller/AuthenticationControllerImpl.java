package com.almadavic.securitystandard.controller;


import com.almadavic.securitystandard.config.securityConfig.Token;
import com.almadavic.securitystandard.dto.request.Login;
import com.almadavic.securitystandard.service.serviceAction.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@RestController   // Identificando  que é um rest-controller
@RequestMapping(value = "/auth")   // Recurso para "encontrar" esse controller
@Primary // Essa vai ser a implementação a ser carregada caso tenha mais de 1.
public class AuthenticationControllerImpl implements AuthenticationController {   // Controller para fazer a autenticação

    private final AuthenticationService authService;  // Service onde terá a lógica relacionada a autenticação do usuário no sistema.

    @Override
    @PostMapping // Método HTTP POST
    public ResponseEntity<Token> authenticate(Login loginData) { // Método para se autenticar no client.

        Token token = authService.authenticate(loginData); //  Token que será retornado para o Client.

        return ResponseEntity.ok().body(token);
    }

}
