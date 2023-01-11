package com.almadavic.securitystandard.service.serviceAction;


import com.almadavic.securitystandard.dto.response.UserMonitoringDTO;
import com.almadavic.securitystandard.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service // Indica que é uma camada de serviço , o spring vai gerenciar automaticamente.
@Primary // Essa vai ser a implementação principal a ser carregada.
public class TokenServiceImpl implements TokenService { // Serviço relacionado ao token.

    @Value("${jwt.expiration}") // var de ambiente , localização externa - application(test,prod).properties
    private String expiration; // tempo de expiração do token

    @Value("${jwt.secret}") // var de ambiente , localização externa - application(test,prod).properties
    private String secret; // regra de como o token será codificado.

    @Override
    public String generateToken(Authentication authentication) { // Método que gera um token.

        User logged = (User) authentication.getPrincipal();

        Date today = new Date();

        Date dateExpiration = new Date(today.getTime() + Long.parseLong(expiration));

        UserMonitoringDTO userDTO = new UserMonitoringDTO(logged);

        return Jwts.builder()                          // -> método cria o hash (token) inteiro com dados do usuario, segurança e alghoritmo de codificação e compacta.
                .setSubject(userDTO.getId())
                .claim("username", userDTO.getNickname())
                .claim("email", userDTO.getEmail())
                .claim("roles", userDTO.getRolesDTO())
                .setIssuedAt(today)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    @Override
    public boolean isTokenValid(String token) { // Método que verifica se o Token é válido.
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getIdUser(String token) { // Método que recupera o Id do Usuário pelo token.
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
