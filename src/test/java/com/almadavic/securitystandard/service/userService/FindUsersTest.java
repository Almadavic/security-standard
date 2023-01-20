package com.almadavic.securitystandard.service.userService;


import com.almadavic.securitystandard.dto.response.UserMonitoringDTO;
import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.repository.UserRepository;
import com.almadavic.securitystandard.service.customException.ResourceNotFoundException;
import com.almadavic.securitystandard.service.serviceAction.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles(value = "test") // Quando o teste for rodado, ele será rodado em ambiente de teste.
@SpringBootTest
public class FindUsersTest { // Classe que testa o service associado com a funcionalidade de encontrar usuários no sistema.

    @Autowired
    private UserService userService; // Usado para testar os métodos findById  e findAll.

    @Autowired
    private UserRepository userRepository; // Usado para buscar um usuário do banco.

    @Test
    @Transactional // Esse annotation foi utilizado pois estava tendo problemas relacionados ao proxy depois da atualização do Spring.
    void findUsersParameterAdministrator() {

        Pageable pageable = PageRequest.of(0, 10); // Páginação

        Page<UserMonitoringDTO> usersDto = userService.findAll(pageable, "ADMIN"); // Quando é passado a paginação e o nome do role, o método deve retornar uma página
                                                                                            // com os usuários que tenham essa role.
        boolean containsRoleUser = false;

        for (UserMonitoringDTO userDTO : usersDto) {

            if (!userDTO.getRolesDTO().get(0).getRoleName().equals("ROLE_ADMIN")) {       // Lógica para verificar se o método não está retornando usuários com  uma role
                containsRoleUser = true;                                                  // que não deveria.
            }

            Assertions.assertFalse(containsRoleUser);

        }

    }

    @Test
    @Transactional // Esse annotation foi utilizado pois estava tendo problemas relacionados ao proxy depois da atualização do Spring.
    void findUsersParameterUser() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<UserMonitoringDTO> usersDto = userService.findAll(pageable, "USER");

        boolean containsRoleAdministrator = false;

        for (UserMonitoringDTO userDTO : usersDto) {

            if (!userDTO.getRolesDTO().get(0).getRoleName().equals("ROLE_USER")) {
                containsRoleAdministrator = true;
            }

            Assertions.assertFalse(containsRoleAdministrator);

        }

    }

    @Test
    @Transactional
    void findById() {

        User user = userRepository.findByEmail("admin@hotmail.com") // É encontrado um usuário no banco de dados pelo e-mail.
                .orElseThrow(()-> new ResourceNotFoundException("The user wasn't found on database\"")); // Caso não tenha um usuário no banco com o e-mail informado,
                                                                                                         // será lançada uma exception.
        String id = user.getId(); // Id do usuário.

        UserMonitoringDTO userDTO = userService.findById(id); // Retorna um dto do usuário.

        Assertions.assertEquals(id, userDTO.getId()); // O id do DTO tem que ser o mesmo do usuário no banco.

    }

}

