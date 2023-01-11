package com.almadavic.securitystandard.config.exceptionConfig.handler;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class ForbiddenHandler extends AuthorizationAuthenticationHandler implements AccessDeniedHandler {
    // Classe trata o erro forbidden e retorna pro usuário de uma maneira mais amigável

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {


        if (!response.isCommitted()) {

            status = HttpStatus.FORBIDDEN.value();
            error = "Forbidden";
            messageError = "Access Denied";

            responseClient(request, response, status, error, messageError);

        }
    }

}
