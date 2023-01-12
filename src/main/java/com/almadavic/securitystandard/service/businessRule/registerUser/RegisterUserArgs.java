package com.almadavic.securitystandard.service.businessRule.registerUser;


import com.almadavic.securitystandard.dto.request.RegisterUserDTO;
import com.almadavic.securitystandard.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class RegisterUserArgs { // Argumentos necessários para poder verificar se um usuário pode ser registrado no banco.

    private RegisterUserDTO registerData; // dados do user passado pelo próprio usuário.
    private UserRepository userRepository; // Será utilizado para fazer ligação com o banco.

}
