package com.almadavic.securitystandard.controller;


import com.almadavic.securitystandard.dto.request.LoginDTO;
import com.almadavic.securitystandard.service.serviceAction.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
public class ClassTestParent {

    protected final int forbidden = 403; // Esse status significa que a pessoa não está logada
    protected final int unauthorized = 401; // Indica que a pessoa está logada mas não tem acesso para acessar tal recurso.
    protected final int badRequest = 400;
    protected final int ok = 200;
    protected final int internalServerError = 500;
    protected final int notFound = 404;
    protected final int created = 201;

    @Autowired
    protected MockMvc mockMvc; // Serve para fazer MOCK, no caso, estamos testando a autorização através de MOCK.

    @Autowired
    protected ObjectMapper objectMapper; // Utilizado para enviar um objeto JSON na requisição.

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    protected String authenticate(LoginDTO loginData) { // Authentica o usuário

        UsernamePasswordAuthenticationToken login = loginData.toConvert();   // converter os dados passado pelo usuario em um token de autenticação

        Authentication authentication = authManager.authenticate(login); // autenticar usuário com base nos dados informados por ele

        String token = tokenService.generateToken(authentication); // Gera o token

        return "Bearer " + token; // Retorna o token

    }

}
