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
public class RegistrationUserTest {

    @Autowired
    private UserService userService;

    @Test
    void returnRegisterData() {

        RegisterUserDTO userRequestDTO = new RegisterUserDTO("paulo", "paulo@hotmail.com", "1234567", "1234567");

        UserMonitoringDTO userResponseDTO = userService.register(userRequestDTO);

        Assertions.assertEquals(userRequestDTO.getNickname(), userResponseDTO.getNickname());
        Assertions.assertEquals(userRequestDTO.getEmail(), userResponseDTO.getEmail());
        Assertions.assertEquals(userResponseDTO.getRolesDTO().get(0).getRoleName(), RoleName.ROLE_USER.name());

    }

}
