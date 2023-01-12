package com.almadavic.securitystandard.service.serviceAction;


import org.springframework.security.core.Authentication;


public interface TokenService {

    String generateToken(Authentication authentication); // método para gerar o token.

    boolean isTokenValid(String token); // método para verificar se o token vindo da requisição é valido.

    String getIdUser(String token); // Método para obter o id do usuário através do token.

}
