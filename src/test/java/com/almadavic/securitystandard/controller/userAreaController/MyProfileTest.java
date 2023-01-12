package com.almadavic.securitystandard.controller.userAreaController;


import com.almadavic.securitystandard.controller.ClassTestParent;
import com.almadavic.securitystandard.dto.request.LoginDTO;
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

        LoginDTO loginData = new LoginDTO("admin@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get(path)
                        .header("Authorization", token))
                .andExpect(status().is(ok));

    }

    @Test
    void myProfileUserLogged() throws Exception { // Teste deve passar, acessar o recurso com um usuário role =user logado.

        LoginDTO loginData = new LoginDTO("user2@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get(path)
                        .header("Authorization", token))
                .andExpect(status().is(ok));

    }

}
