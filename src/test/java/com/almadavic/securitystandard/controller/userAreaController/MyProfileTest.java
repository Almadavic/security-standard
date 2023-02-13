package com.almadavic.securitystandard.controller.userAreaController;


import com.almadavic.securitystandard.controller.ClassTestParent;
import com.almadavic.securitystandard.dto.request.Login;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
@SpringBootTest // Indica que estamos fazendo testes com spring, onde a aplicação sobe.
@AutoConfigureMockMvc // Utilizaremos mocks nos testes
public class MyProfileTest extends ClassTestParent { // Classe testa o recurso /myprofile

    private final String path = "/userarea/myprofile";

    @Test
    void myProfileAdmLogged() throws Exception { // Teste deve passar, acessar o recurso com um usuário role = administrador logado.

        Login loginData = new Login("admin@hotmail.com", "123456"); // DTO de Login que passamos na requisição para logar.

        String token = authenticate(loginData); // Loga o usuário no sistema através do DTO e retorna o token pora ser enviado nas próxima requisição.

        mockMvc.perform(get(path) // Caminho da requisição.
                        .header("Authorization", token)) // Token que será enviado na requisição.
                .andExpect(status().is(ok)); // Status de resposta esperado.

    }

    @Test
    void myProfileUserLogged() throws Exception { // Teste deve passar, acessar o recurso com um usuário role = user logado.

        Login loginData = new Login("user2@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get(path)
                        .header("Authorization", token))
                .andExpect(status().is(ok));

    }

}
