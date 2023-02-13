package com.almadavic.securitystandard.controller;


import com.almadavic.securitystandard.dto.request.Login;
import com.almadavic.securitystandard.service.serviceAction.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
public class ClassTestParent { // As classes que vão herdar essa classe, vão ter acesso aos atributos e ao método, esse foi o objetivo de criação dessa classe.

    protected final int forbidden = 403; // Indica que a pessoa está logada mas não tem acesso para acessar tal recurso.
    protected final int unauthorized = 401;  // Indica que a pessoa não está logada, portanto não pode acessar o recurso.
    protected final int badRequest = 400; // Indica que foi passado algo errado na requisição.
    protected final int ok = 200; // Indica que está tudo certo, não teve nenhum problema.
    protected final int internalServerError = 500; // Indica que ocorreu algum erro no servidor.
    protected final int notFound = 404; // Indica que o recurso não foi encontrado.
    protected final int created = 201; // Indica que um recurso foi criado no sistema.

    @Autowired
    protected MockMvc mockMvc; // Serve para fazer MOCK, no caso, estamos testando a autorização através de MOCK.

    @Autowired
    protected ObjectMapper objectMapper; // Utilizado para enviar um objeto JSON na requisição.

    @Autowired
    private AuthenticationManager authManager; // Injeção de dependência para logar um usuário no sistema.

    @Autowired
    private TokenService tokenService; // Injeção de dependência para gerar o token para ser utilizado em uma próxima requisição.

    protected String authenticate(Login loginData) { // Autentica o usuário

        UsernamePasswordAuthenticationToken login = loginData.toConvert();   // converter os dados passado pelo usuario em um token de autenticação.

        Authentication authentication = authManager.authenticate(login); // autenticar usuário com base nos dados informados por ele.

        String token = tokenService.generateToken(authentication); // Gera o token.

        return "Bearer " + token; // Retorna o token ao client.

    }

}
