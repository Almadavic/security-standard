package com.almadavic.securitystandard.service.serviceAction;

import com.almadavic.securitystandard.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class ServiceParent {

    @Autowired
    protected UserMapper userMapper; // Associado a conversão de entidade em DTO e vice-versa (relacionado a Usuário).

    @Autowired
    protected PasswordEncoder encoder; // injeção de dependencia de Encoder -> Codificar uma senha para ser salva no banco e para fazer validação (match).

}
