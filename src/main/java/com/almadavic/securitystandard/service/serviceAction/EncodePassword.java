package com.almadavic.securitystandard.service.serviceAction;


import org.springframework.security.crypto.password.PasswordEncoder;


public interface EncodePassword { // Essa interface será implementada pelas classes que precisam codificar uma senha.

    default String encodePassword(String password, PasswordEncoder encoder) { // Método que codifica a senha.
        return encoder.encode(password);
    }

}
