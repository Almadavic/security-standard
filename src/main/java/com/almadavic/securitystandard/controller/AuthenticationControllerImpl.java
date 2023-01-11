package com.almadavic.securitystandard.controller;

import com.almadavic.securitystandard.config.securityConfig.Token;
import com.almadavic.securitystandard.dto.request.LoginDTO;
import com.almadavic.securitystandard.service.serviceAction.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@RestController   // Identificando  que é um rest-controller
@RequestMapping(value = "/auth")   // Recurso para "encontrar" esse controller
public class AuthenticationControllerImpl implements AuthenticationController {         // Controller para fazer a autenticação

    private final AuthenticationService authService;  // Injeção de dependencia automatica - > AuthenticationService


    @Override
    @PostMapping  // // Método HTTP POST - Cadastrar / Criar
    public ResponseEntity<Token> authenticate(LoginDTO loginData) { // Método para se autenticar no cliente

        Token token = authService.authenticate(loginData); //  Token que será retornado para o Client.

        return ResponseEntity.ok(token);
    }

}
