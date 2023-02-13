package com.almadavic.securitystandard.controller.authentication;


import com.almadavic.securitystandard.controller.ClassTestParent;
import com.almadavic.securitystandard.dto.request.Login;
import com.almadavic.securitystandard.service.customException.DatabaseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
@SpringBootTest // Indica que estamos fazendo testes com spring, onde a aplicação sobe (vai para o ar).
@AutoConfigureMockMvc // Utilizaremos mocks nos testes
public class AuthenticationTest extends ClassTestParent {  // Classe testa a autenticação de um usuário no sistema.

    private final String path = "/auth"; // recurso (URI)

    @Test
    void loginFailPasswordWrong() throws Exception { // Login deve falhar pois a senha está incorreta.

        Login loginData = new Login("admin@hotmail.com", "1234567");

        mockMvc.perform(post(path)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().is(badRequest))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DatabaseException))
                .andExpect(result -> assertEquals("E-mail and / or password is / are wrong!"
                        , result.getResolvedException().getMessage()));

    }

    @Test
    void loginFailEmailWrong() throws Exception { // Login deve falhar pois o e-mail está incorreto.

        Login loginData = new Login("adminn@hotmail.com", "123456");

        mockMvc.perform(post(path)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().is(badRequest))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DatabaseException))
                .andExpect(result -> assertEquals("E-mail and / or password is / are wrong!"
                        , result.getResolvedException().getMessage()));

    }

    @Test
    void loginOk() throws Exception { // Login deve ser feito com sucesso.

        Login loginData = new Login("admin@hotmail.com", "123456");

        mockMvc.perform(post(path)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginData)))
                .andExpect(status().is(ok));

    }

}
