package com.almadavic.securitystandard.service.serviceAction;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface EncodePassword {

    default String encodePassword(String password, PasswordEncoder encoder) { // Esse método é comum para ambas as classes, método que codifica a senha.
        return encoder.encode(password);
    }

}
