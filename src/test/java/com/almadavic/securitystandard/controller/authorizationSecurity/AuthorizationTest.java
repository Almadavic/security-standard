package com.almadavic.securitystandard.controller.authorizationSecurity;


import com.almadavic.securitystandard.controller.ClassTestParent;
import com.almadavic.securitystandard.dto.request.LoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
@SpringBootTest // Indica que estamos fazendo testes com spring, onde a aplicação sobe.
@AutoConfigureMockMvc // Utilizaremos mocks nos testes
public class AuthorizationTest extends ClassTestParent { // Classe testa o acesso aos recursos do sistema (autorização e autenticação).


    // ---------------------- | TESTES DE ACESSO NEGADO | --------------------------||

    @Test
    void accessMyProfileDeniedNoUserLogged() throws Exception { // Método testa o acesso ao recurso /userarea/myprofile sem nenhum usuário logado.O sistema não deve deixar

        // Método não tem nenhum usuário logado.
        // Qualquer usuário logado tem acesso á esse método.

        mockMvc.perform(get("/userarea/myprofile"))
                .andExpect(status().is(unauthorized));
    }

    @Test
    void accessChangePasswordDeniedNoUserLogged() throws Exception { // Método teste o acesso ao recurso /userarea/changepassword sem nenhum uuário logado.O sistema não deve deixar

        // Método não tem nenhum usuário logado.
        // Qualquer usuário logado tem acesso á esse método.

        mockMvc.perform(put("/userarea/changepassword"))
                .andExpect(status().is(unauthorized));
    }

    @Test
    void accessFindAllDeniedNoUserLogged() throws Exception { // Método teste o acesso ao recurso /users/findall sem nenhum usuário logado. O sistema não deve deixar.

        // Método não tem nenhum usuário logado.
        // Apenas administradores podem acessar esse recurso.

        mockMvc.perform(get("/users"))
                .andExpect(status().is(unauthorized));
    }

    @Test
        // Método testa o acesso ao recurso /users/findall com usuário role= user logado. Sistema não deve deixar.
    void accessFindAllDeniedRoleUserLogged() throws Exception {

        // Método tem usuário logado.
        // Apenas administradores podem acessar esse recurso.

        LoginDTO loginData = new LoginDTO("user1@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get("/users")
                        .header("Authorization", token))
                .andExpect(status().is(forbidden));
    }

    @Test
    void accessFindByIdDeniedNoUserLogged() throws Exception { // Método testa o acesso ao recurso /users/{id} sem usuário logado. O sistema não deve deixar.

        // Método não tem nenhum usuário logado.
        // Apenas administradores podem acessar esse recurso.

        mockMvc.perform(get("/users/{id}", "teste")) // Não tem nenhuma variavel para id, mas oq importa é dar o erro unhautorizen.
                .andExpect(status().is(unauthorized));
    }

    @Test
        // Método testa o acesso ao recurso /users/{id} com usuário role=user logado. O sistema não deve deixar.
    void accessFindByIdDeniedRoleUserLogged() throws Exception {

        // Método tem usuário logado.
        // Apenas administradores podem acessar esse recurso.

        LoginDTO loginData = new LoginDTO("user1@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get("/users/{id}", "teste") // Não tem nenhuma variavel para id, mas oq importa é dar o erro forbidden.
                        .header("Authorization", token))
                .andExpect(status().is(forbidden));
    }

    @Test
    void accessAnyOtherResourceDeniedNoUserLogged() throws Exception { // Método testa o acesso a algum recurso inválido no sistem, usuario não logado. O sistema não deve deixar.

        // Ninguém pode acessar esse recurso.

        mockMvc.perform(get("/recursoinvalido"))
                .andExpect(status().is(unauthorized));
    }

    @Test
    void accessAnyOtherResourcedDeniedRoleAdminLogged() throws Exception { // Método testa o acesso a algum recurso inválido no sistema, usuário role=administrador logado. O sistema não deve deixar.

        // Ninguém pode acessar esse recurso.

        LoginDTO loginData = new LoginDTO("admin@hotmail.com", "123456");

        String token = authenticate(loginData);


        mockMvc.perform(get("/recursoinvalido")
                        .header("Authorization", token))
                .andExpect(status().is(notFound));

    }

    @Test
    void accessAnyOtherResourcedDeniedRoleUserLogged() throws Exception { // Método testa o acesso a algum recurso inválido no sistema, usuário role=user logado. O sistema não deve deixar.

        // Ninguém pode acessar esse recurso.

        LoginDTO loginData = new LoginDTO("user1@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get("/recursoinvalido")
                        .header("Authorization", token))
                .andExpect(status().is(notFound));


    }


    // ---------------------- | TESTES DE ACESSO LIBERADO | --------------------------||

    @Test
    void accessAuthentication() throws Exception { // Método testa o acesso au recurso /auth. O sistema deve deixar.

        // Método não tem nenhum usuário logado.
        // Qualquer um pode acessar esse recurso.

        mockMvc.perform(post("/auth"))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));
    }

    @Test
    void accessMyProfileAllowedRoleAdmin() throws Exception {  // Método testa o acesso ao recurso /userarea/myprofile , usuario role = administrador. O sistema deve deixar.

        // Método tem administrador logado.
        // Qualquer um pode (logado) pode acessar esse método.

        LoginDTO loginData = new LoginDTO("user1@hotmail.com", "123456");

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

        LoginDTO loginData = new LoginDTO("user1@hotmail.com", "123456");

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

        LoginDTO loginData = new LoginDTO("admin@hotmail.com", "123456");

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

        LoginDTO loginData = new LoginDTO("user1@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(put("/userarea/changepassword")
                        .header("Authorization", token))
                .andExpect(result -> assertNotEquals(unauthorized, result.getResponse().getStatus()))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));

    }

    @Test
    void accessRegisterUser() throws Exception { // Método testa o acesso au recurso /auth. O sistema deve deixar.


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

        LoginDTO loginData = new LoginDTO("admin@hotmail.com", "123456");

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

        LoginDTO loginData = new LoginDTO("admin@hotmail.com", "123456");

        String token = authenticate(loginData);

        mockMvc.perform(get("/users/{id} ", "teste")
                .header("Authorization", token))
                .andExpect(result -> assertNotEquals(unauthorized, result.getResponse().getStatus()))
                .andExpect(result -> assertNotEquals(forbidden, result.getResponse().getStatus()));

    }

}

