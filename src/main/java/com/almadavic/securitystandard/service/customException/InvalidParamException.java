package com.almadavic.securitystandard.service.customException;


import java.io.Serial;


public class InvalidParamException extends RuntimeException { // Erro no geral no Banco de Dados

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidParamException(String msg) {
        super(msg);
    }

}
