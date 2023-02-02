package com.almadavic.securitystandard.service.businessRule.findUsersByParameter;


import com.almadavic.securitystandard.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

public record FindUsersArgs(UserRepository userRepository, Pageable pageable,
                            String roleName) { // Argumentos necessários para poder verificar qual pagina de usuários será retornada.

}
