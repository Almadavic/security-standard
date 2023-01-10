package com.almadavic.securitystandard.service.exception;

import java.io.Serial;

public class DatabaseException extends RuntimeException { // Erro no geral no Banco de Dados

    @Serial
    private static final long serialVersionUID = 1L;

    public DatabaseException(String msg) {
        super(msg);
    }

}
