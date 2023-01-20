package com.almadavic.securitystandard.service.customException;


import java.io.Serial;


public class ResourceNotFoundException extends RuntimeException { // Quando um recurso não é encontrado.

    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}
