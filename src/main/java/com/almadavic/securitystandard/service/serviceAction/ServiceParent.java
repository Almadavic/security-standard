package com.almadavic.securitystandard.service.serviceAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class ServiceParent {

    @Autowired
    protected PasswordEncoder encoder; // injeção de dependencia de Encoder -> Codificar uma senha para ser salva no banco e para fazer validação (match).

}