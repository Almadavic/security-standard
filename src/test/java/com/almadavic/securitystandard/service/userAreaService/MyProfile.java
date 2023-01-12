package com.almadavic.securitystandard.service.userAreaService;

import com.almadavic.securitystandard.dto.response.UserDTO;
import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.repository.UserRepository;
import com.almadavic.securitystandard.service.serviceAction.UserAreaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
@SpringBootTest
public class MyProfile {

    @Autowired
    private UserAreaService userAreaService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void loggedUserData() {

        User user = userRepository.findByEmail("user2@hotmail.com").get();

        UserDTO userDTO = userAreaService.myProfile(user);

        Assertions.assertEquals(user.getNickname(),userDTO.getNickname());
        Assertions.assertEquals(user.getUsername(),userDTO.getEmail());

    }

}
