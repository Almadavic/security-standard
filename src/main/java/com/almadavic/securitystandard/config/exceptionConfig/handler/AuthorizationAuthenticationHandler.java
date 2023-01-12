package com.almadavic.securitystandard.config.exceptionConfig.handler;


import com.almadavic.securitystandard.config.exceptionConfig.standardError.commonStandardError.StandardError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthorizationAuthenticationHandler { // A classe será utilizada por 2 handles, essa classe atual tem o papel de retornar a
    //resposta para cliente. Essa resposta será enviado dependendo em qual handler foi chamado, Forbidden ou Unauthorized.

    protected int status; // status da resposta

    protected String error; // o error

    protected String messageError; // mensagem do erro

    private final ObjectMapper objectMapper = new ObjectMapper(); // "Transformar" o objeto em json

    protected void responseClient(HttpServletRequest request, HttpServletResponse response, int status, String error, String messageError) { // Indica a resposta que será retornada pro cliente.
        try {

            objectMapper.registerModule(new JavaTimeModule());

            response.setStatus(status);
            response.setContentType("application/json");

            String uri = request.getRequestURI();

            StandardError err = new StandardError(status, error, messageError, uri);

            response.getWriter().write(objectMapper.writeValueAsString(err));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
