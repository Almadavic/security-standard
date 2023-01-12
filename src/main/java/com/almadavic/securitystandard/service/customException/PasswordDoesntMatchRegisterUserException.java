package com.almadavic.securitystandard.service.customException;


import java.io.Serial;


public class PasswordDoesntMatchRegisterUserException extends RuntimeException { // Erro quando o usuário tenta registrar um usuário e os campos da senha não correspondem

    @Serial
    private static final long serialVersionUID = 1L;

    public PasswordDoesntMatchRegisterUserException(String msg) {
        super(msg);
    }

}
