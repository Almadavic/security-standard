package com.almadavic.securitystandard.controller.userAreaController;


import com.almadavic.securitystandard.controller.ClassTestParent;
import com.almadavic.securitystandard.dto.request.ChangePassword;
import com.almadavic.securitystandard.dto.request.Login;
import com.almadavic.securitystandard.service.customException.DatabaseException;
import com.almadavic.securitystandard.service.customException.SamePasswordException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
@SpringBootTest // Indica que estamos fazendo testes com spring, onde a aplicação sobe.
@AutoConfigureMockMvc // Utilizaremos mocks nos testes
public class ChangePasswordTest extends ClassTestParent {  // Classe testa a funcionalidade de alterar uma senha no sistema.

    private final String path = "/userarea/changepassword";

    @Test
    void passwordDoenstMatchDataBasePassword() throws Exception { // Alteração de senha deve falhar pois o usuário está passando sua senha incorreta.

        Login loginData = new Login("user2@hotmail.com", "123456"); // DTO de Login que passamos na requisição para logar.

        String token = authenticate(loginData); // Loga o usuário no sistema através do DTO e retorna o token pora ser enviado nas próxima requisição.

        ChangePassword changePasswordDTO = new ChangePassword("12345678", "13553353553"); // DTO para alterar a senha

        mockMvc.perform(put(path) // Caminho da requisição
                        .contentType("application/json") // O tipo do conteúdo
                        .header("Authorization", token) // O token que será enviado na requisição.
                        .content(objectMapper.writeValueAsString(changePasswordDTO))) // O conteúdo que será enviado.
                .andExpect(status().is(badRequest)) // Erro que deve ocorrer.
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DatabaseException)) // Tipo de exception esperada.
                .andExpect(result -> assertEquals("The password is not correct (not match)" // Mensagem da exception esperada.
                        , result.getResolvedException().getMessage()));

    }

    @Test
    void passwordIsEqualTheLastOne() throws Exception { // Alteração da senha deve falhar pois o usuário está passando o valor da nova senha igual da antiga.

        Login loginData = new Login("admin@hotmail.com", "123456");

        String token = authenticate(loginData);

        ChangePassword changePasswordDTO = new ChangePassword("123456", "123456");

        mockMvc.perform(put(path)
                        .contentType("application/json")
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(changePasswordDTO)))
                .andExpect(status().is(internalServerError))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof SamePasswordException))
                .andExpect(result -> assertEquals("Your new password cannot be equal the last one"
                        , result.getResolvedException().getMessage()));

    }

    @Test
    void changePasswordSuccessFlow() throws Exception { // Alteração de senha deve ser feita com sucesso.

        Login firstLogin = new Login("user1@hotmail.com", "123456");

        String token = authenticate(firstLogin);

        String oldPassword = "123456";

        String newPassword = "1234567";

        ChangePassword changePasswordDTO = new ChangePassword(oldPassword, newPassword);

        passwordChanged(changePasswordDTO, token); // Senha alterada de oldPassword para newPassword;

        Login loginDataAttempt1 = new Login("user1@hotmail.com", oldPassword);

        enterSystemFail(loginDataAttempt1); // Usuário tenta logar com a senha incorreta ( Senha antiga)

        Login loginDataAttempt2 = new Login("user1@hotmail.com", newPassword);

        enterSystemSuccess(loginDataAttempt2); // Usuário tenta logar com a senha correta ( Nova senha)

    }

    private void passwordChanged(ChangePassword changePasswordDTO, String token) throws Exception { // Mudança de senha.

        mockMvc.perform(put(path)
                        .contentType("application/json")
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(changePasswordDTO)))
                .andExpect(status().is(ok))
                .andExpect(result -> assertEquals("Password changed successfully!",
                        result.getResponse().getContentAsString()));
    }

    private void enterSystemFail(Login loginDataAttempt1) throws Exception { // Usuário tenta logar com a senha antiga.

        mockMvc.perform(post("/auth")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginDataAttempt1)))
                .andExpect(status().is(badRequest))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DatabaseException))
                .andExpect(result -> assertEquals("E-mail and / or password is / are wrong!"
                        , result.getResolvedException().getMessage()));
    }

    private void enterSystemSuccess(Login loginDataAttempt2) throws Exception { // Usuário tenta logar com a nova senha.

        mockMvc.perform(post("/auth")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginDataAttempt2)))
                .andExpect(status().is(ok));
    }

}
