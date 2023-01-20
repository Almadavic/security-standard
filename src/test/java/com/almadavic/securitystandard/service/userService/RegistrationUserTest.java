package com.almadavic.securitystandard.service.userService;


import com.almadavic.securitystandard.dto.request.RegisterUserDTO;
import com.almadavic.securitystandard.dto.response.UserMonitoringDTO;
import com.almadavic.securitystandard.enumerated.RoleName;
import com.almadavic.securitystandard.service.serviceAction.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
@SpringBootTest
public class RegistrationUserTest {  // Classe que testa o service associado com a funcionalidade de registrar um usuário no sistema.

    @Autowired
    private UserService userService; // Usado para testar o método de registrar um usuário no sistema.

    @Test
    void returnRegisterData() {

        RegisterUserDTO userRequestDTO = new RegisterUserDTO("paulo", "paulo@hotmail.com", "1234567", "1234567");  // DTO de registro de usuário.

        UserMonitoringDTO userResponseDTO = userService.register(userRequestDTO); // O método recebe um DTO de registro e retorna um DTO de usuário.

        Assertions.assertEquals(userRequestDTO.getNickname(), userResponseDTO.getNickname());  // O nome do DTO tem que ser o mesmo do usuário no banco.
        Assertions.assertEquals(userRequestDTO.getEmail(), userResponseDTO.getEmail());  // O email do DTO tem que ser o mesmo do usuário no banco.
        Assertions.assertEquals(userResponseDTO.getRolesDTO().get(0).getRoleName(), RoleName.ROLE_USER.name()); // A role do usuário cadastrado, tem que ser a role = USER.

    }

}
