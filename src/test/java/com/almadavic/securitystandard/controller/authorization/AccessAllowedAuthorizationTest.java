package com.almadavic.securitystandard.controller.authorization;


import com.almadavic.securitystandard.controller.ClassTestParent;
import com.almadavic.securitystandard.dto.request.Login;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
@SpringBootTest // Indica que estamos fazendo testes com spring, onde a aplicação sobe.
@AutoConfigureMockMvc // Utilizaremos mocks nos testes
public class AccessAllowedAuthorizationTest extends ClassTestParent { // Classe testa o acesso aos recursos do sistema (autorização e autenticação) que estão autorizados.

    @Test
    void accessSwagger() throws Exception { // Método testa o acesso ao recurso /swagger-ui/index.html. O sistema deve deixar.

        // Método não precisa de autenticação e nema autorização.

        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(result -> assertEquals(ok, result.getResponse().getStatus())); // Tem que retornar esse status,  é o único status que deve ser retornado desse recurso.

    }
    
    @Test
    void accessAuthentication() throws Exception { // Método testa o acesso ao recurso /auth. O sistema deve deixar.

        // Método não tem nenhum usuário logado.
        // Qualquer um pode acessar esse recurso.

        mockMvc.perform(post("/auth"))
                .andExpect(result -> assertNotEquals(unauthorized, result.getResponse().getStatus()))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));

    }

    @Test
    void accessMyProfileAllowedRoleAdmin() throws Exception {  // Método testa o acesso ao recurso /userarea/myprofile , usuario role = administrador. O sistema deve deixar.

        // Método tem administrador logado.
        // Qualquer um pode (logado) pode acessar esse método.

        Login loginData = new Login("admin@hotmail.com", "123456");

        String token = authenticate(loginData);


        mockMvc.perform(get("/userarea/myprofile")
                        .header("Authorization", token))
                .andExpect(result -> assertNotEquals(unauthorized, result.getResponse().getStatus()))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));


    }

    @Test
    void accessMyProfileAllowedRoleUser() throws Exception {  // Método testa o acesso ao recurso /userarea/myprofile , usuario role = user. O sistema deve deixar.

        // Método tem usuário logado.
        // Qualquer um pode (logado) pode acessar esse método.

        Login loginData = new Login("user1@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get("/userarea/myprofile")
                        .header("Authorization", token))
                .andExpect(result -> assertNotEquals(unauthorized, result.getResponse().getStatus()))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));

    }

    @Test
    void accessChangePasswordAllowedRoleAdmin() throws Exception {  // Método testa o acesso ao recurso /userarea/changepassword , usuario role = administrador. O sistema deve deixar.

        // Método tem administrador logado.
        // Qualquer um pode (logado) pode acessar esse método.

        Login loginData = new Login("admin@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(put("/userarea/changepassword")
                        .header("Authorization", token))
                .andExpect(result -> assertNotEquals(unauthorized, result.getResponse().getStatus()))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));


    }

    @Test
    void accessChangePasswordAllowedRoleUser() throws Exception {  // Método testa o acesso ao recurso /userarea/changepassword , usuario role = user. O sistema deve deixar.

        // Método tem usuário logado.
        // Qualquer um pode (logado) pode acessar esse método.

        Login loginData = new Login("user1@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(put("/userarea/changepassword")
                        .header("Authorization", token))
                .andExpect(result -> assertNotEquals(unauthorized, result.getResponse().getStatus()))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));

    }

    @Test
    void accessRegisterUser() throws Exception { // Método testa o acesso ao recurso /auth. O sistema deve deixar.


        // Método não tem nenhum usuário logado.
        // Qualquer um pode acessar esse recurso.

        mockMvc.perform(post("/users/register"))
                .andExpect(result -> assertNotEquals(unauthorized, result.getResponse().getStatus()))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));

    }

    @Test
    void accessFindAllAllowedRoleAdminLogged() throws Exception { // Método testa o acesso ao recurso /users/findall , usuario role = administrador. O sistema deve deixar.

        // Método tem administrador logado.
        // Apenas administradores podem acessar esse recurso.

        Login loginData = new Login("admin@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get("/users")
                        .header("Authorization", token))
                .andExpect(result -> assertNotEquals(unauthorized, result.getResponse().getStatus()))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));

    }

    @Test
    void accessFindByIdAllowedRoleAdminLogged() throws Exception { // Método testa o acesso ao recurso /userarea/{id} , usuario role = administrador. O sistema deve deixar.

        // Método tem administrador logado.
        // Apenas administradores podem acessar esse recurso.

        Login loginData = new Login("admin@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get("/users/{id} ", "teste")
                        .header("Authorization", token))
                .andExpect(result -> assertNotEquals(unauthorized, result.getResponse().getStatus()))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));

    }

    @Test
    void accessAnyOtherResourcedRoleAdminLogged() throws Exception { // Método testa o acesso a algum recurso inválido no sistema, usuário role=administrador logado. Deve retornar status not found.


        Login loginData = new Login("admin@hotmail.com", "123456");

        String token = authenticate(loginData);


        mockMvc.perform(get("/recursoinvalido")
                        .header("Authorization", token))
                .andExpect(status().is(notFound));

    }

    @Test
    void accessAnyOtherResourceRoleUserLogged() throws Exception { // Método testa o acesso a algum recurso inválido no sistema, usuário role=user logado. Deve retornar status not faund


        Login loginData = new Login("user1@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get("/recursoinvalido")
                        .header("Authorization", token))
                .andExpect(status().is(notFound));

    }

}

