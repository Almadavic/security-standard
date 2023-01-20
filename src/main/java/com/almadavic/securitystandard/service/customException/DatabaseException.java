package com.almadavic.securitystandard.service.customException;


import java.io.Serial;


public class DatabaseException extends RuntimeException { // Erro generalizado do Banco de Dados.

    @Serial
    private static final long serialVersionUID = 1L;

    public DatabaseException(String msg) {
        super(msg);
    }

}
