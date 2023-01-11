package com.almadavic.securitystandard.service.serviceAction;


import com.almadavic.securitystandard.config.securityConfig.Token;
import com.almadavic.securitystandard.dto.request.LoginDTO;
import org.springframework.security.authentication.AuthenticationManager;

public interface AuthenticationService {  // Service de autenticação deve implementar essa interface.

    Token authenticate(LoginDTO form, AuthenticationManager authManager); // Método com a lógica para se autenticar no sistema.

}
