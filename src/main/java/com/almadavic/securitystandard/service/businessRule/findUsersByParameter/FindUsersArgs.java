package com.almadavic.securitystandard.service.businessRule.findUsersByParameter;


import com.almadavic.securitystandard.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;


@AllArgsConstructor
@Getter
public class FindUsersArgs { // Argumentos necessários para poder verificar qual pagina de usuários será retornada.

    private UserRepository userRepository;  // Será utilizado para fazer ligação com o banco.
    private Pageable pageable; // Será utilizado para retornar a pagina de usuários.
    private String roleName;  // Nome da role passada pelo usuário.

}
