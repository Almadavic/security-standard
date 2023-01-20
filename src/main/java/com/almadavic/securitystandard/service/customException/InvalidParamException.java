package com.almadavic.securitystandard.service.customException;


import java.io.Serial;


public class InvalidParamException extends RuntimeException { // Erro quando o usuário passa algum paramêtro inválido na request.

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidParamException(String msg) {
        super(msg);
    }

}
