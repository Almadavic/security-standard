package com.almadavic.securitystandard.controller.userAreaController;


import com.almadavic.securitystandard.controller.ClassTestParent;
import com.almadavic.securitystandard.dto.request.ChangePasswordDTO;
import com.almadavic.securitystandard.dto.request.LoginDTO;
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

        LoginDTO loginData = new LoginDTO("user2@hotmail.com", "123456");

        String token = authenticate(loginData);

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("12345678", "13553353553");

        mockMvc.perform(put(path)
                        .contentType("application/json")
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(changePasswordDTO)))
                .andExpect(status().is(badRequest))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DatabaseException))
                .andExpect(result -> assertEquals("The password is not correct (not match)"
                        , result.getResolvedException().getMessage()));

    }

    @Test
    void passwordIsEqualTheLastOne() throws Exception { // Alteração da senha deve falhar pois o usuário está passando o valor da nova senha igual da antiga.

        LoginDTO loginData = new LoginDTO("admin@hotmail.com", "123456");

        String token = authenticate(loginData);

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("123456", "123456");

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

        LoginDTO firstLogin = new LoginDTO("user1@hotmail.com", "123456");

        String token = authenticate(firstLogin);

        String oldPassword = "123456";

        String newPassword = "1234567";

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(oldPassword, newPassword);

        passwordChanged(changePasswordDTO, token); // Senha alterada de oldPassword para newPassword;

        LoginDTO loginDataAttempt1 = new LoginDTO("user1@hotmail.com", oldPassword);

        enterSystemFail(loginDataAttempt1); // Usuário tenta logar com a senha incorreta ( Senha antiga)

        LoginDTO loginDataAttempt2 = new LoginDTO("user1@hotmail.com", newPassword);

        enterSystemSuccess(loginDataAttempt2); // Usuário tenta logar com a senha correta ( Nova senha)

    }

    private void passwordChanged(ChangePasswordDTO changePasswordDTO, String token) throws Exception {

        mockMvc.perform(put(path)
                        .contentType("application/json")
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(changePasswordDTO)))
                .andExpect(status().is(ok))
                .andExpect(result -> assertEquals("Password changed successfully!",
                        result.getResponse().getContentAsString()));
    }

    private void enterSystemFail(LoginDTO loginDataAttempt1) throws Exception {

        mockMvc.perform(post("/auth")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginDataAttempt1)))
                .andExpect(status().is(badRequest))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DatabaseException))
                .andExpect(result -> assertEquals("E-mail and / or password is / are wrong!"
                        , result.getResolvedException().getMessage()));
    }

    private void enterSystemSuccess(LoginDTO loginDataAttempt2) throws Exception {

        mockMvc.perform(post("/auth")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginDataAttempt2)))
                .andExpect(status().is(ok));
    }

}
