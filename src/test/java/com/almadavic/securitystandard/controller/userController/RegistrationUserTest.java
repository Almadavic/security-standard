package com.almadavic.securitystandard.controller.userController;


import com.almadavic.securitystandard.controller.ClassTestParent;
import com.almadavic.securitystandard.dto.request.RegisterUserDTO;
import com.almadavic.securitystandard.service.customException.EmailAlreadyRegisteredException;
import com.almadavic.securitystandard.service.customException.NicknameAlreadyRegisteredException;
import com.almadavic.securitystandard.service.customException.PasswordDoesntMatchRegisterUserException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
@SpringBootTest // Indica que estamos fazendo testes com spring, onde a aplicação sobe.
@AutoConfigureMockMvc // Utilizaremos mocks nos testes
public class RegistrationUserTest extends ClassTestParent {  // Classe testa a funcionalidade de cadastrar um usuário no banco.

    @Test
    void nicknameAlreadyRegistered() throws Exception { // Método deve falhar pois já existe um usuário no banco com esse nickname.

        RegisterUserDTO registerDTO = new RegisterUserDTO("admin", "joaomaia@gmail.com", "123456", "123456"); // DTO para se registrar no sistema.

        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().is(internalServerError))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NicknameAlreadyRegisteredException))
                .andExpect(result -> assertEquals("This nickname: " + registerDTO.getNickname() + " already exists in the system"
                        , result.getResolvedException().getMessage()));

    }

    @Test
    void emailAlreadyRegistered() throws Exception {  // Método deve falhar pois já existe um usuário no banco com esse e-mail.

        RegisterUserDTO registerDTO = new RegisterUserDTO("almadaV", "admin@hotmail.com", "123456", "123456"); // DTO para se registrar no sistema.

        mockMvc.perform(post("/users/register") // Caminho da requisição.
                        .contentType("application/json") // // O tipo do conteúdo
                        .content(objectMapper.writeValueAsString(registerDTO))) // O conteúdo que será enviado.
                .andExpect(status().is(internalServerError)) // Erro que deve ocorrer.
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmailAlreadyRegisteredException)) // Tipo de exception esperada.
                .andExpect(result -> assertEquals("This e-mail: " + registerDTO.getEmail() + " already exists in the system" // Mensagem da exception esperada.
                        , result.getResolvedException().getMessage()));

    }

    @Test
    void passwordsDontMatch() throws Exception {  // Método deve falhar pois o usuário está passando 2 senhas que não correspondem.

        RegisterUserDTO registerDTO = new RegisterUserDTO("Larissa", "larissa@hotmail.com", "123456", "1234567");

        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().is(badRequest))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PasswordDoesntMatchRegisterUserException))
                .andExpect(result -> assertEquals("The passwords don't match"
                        , result.getResolvedException().getMessage()));

    }

    @Test
    void registeredSuccess() throws Exception { // O usuário deve conseguir se registrar no sistema com sucesso.

        RegisterUserDTO registerDTO = new RegisterUserDTO("Davi", "davi@hotmail.com", "123456", "123456");

        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().is(created))
                .andExpect(result -> assertEquals(registerDTO.getNickname() + ", your account was registered successfully!",
                        result.getResponse().getContentAsString()));

    }

}
