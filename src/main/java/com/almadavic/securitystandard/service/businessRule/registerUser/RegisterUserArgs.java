package com.almadavic.securitystandard.service.businessRule.registerUser;


import com.almadavic.securitystandard.dto.request.RegisterUserDTO;
import com.almadavic.securitystandard.repository.UserRepository;


public record RegisterUserArgs(RegisterUserDTO registerData,
                               UserRepository userRepository) { // Argumentos necessários para poder verificar se um usuário pode ser registrado no sistema.

}
