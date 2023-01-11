package com.almadavic.securitystandard.config.exceptionConfig.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


public class UnauthorizedHandler extends AuthorizationAuthenticationHandler implements AuthenticationEntryPoint {
    // O Spring retorna 403 ao inves do 401, então tive que criar essa classe
    // que para quando o usuário não estiver autenticado, retornar o 401 ao inves do 403. Quando o usuário está autenticado e não autorizado, o 403 continua
    // funcionando normalmente.

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) { // Método criado para retornar o erro
        // 401 de uma forma mais amigável.

        if (!response.isCommitted()) {


            status = HttpStatus.UNAUTHORIZED.value();
            error = "Unhautorized";
            messageError = "In order to access this recource, you have to be logged in the system";

            responseClient(request, response, status, error, messageError);

        }

    }

}