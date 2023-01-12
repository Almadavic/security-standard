package com.almadavic.securitystandard.service.customException;


import java.io.Serial;


public class SamePasswordException extends RuntimeException { // Quando o usuário vai trocar a senha, e tenta trocar pela a que ele já tinha no banco.

    @Serial
    private static final long serialVersionUID = 1L;

    public SamePasswordException(String msg) {
        super(msg);
    }

}
