package com.almadavic.securitystandard.service.serviceAction;



import com.almadavic.securitystandard.dto.response.RoleDTO;
import com.almadavic.securitystandard.dto.response.UserMonitoringDTO;
import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.util.UserMapper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;


@Service // Indica que é uma camada de serviço , o spring vai gerenciar automaticamente.
@Primary // Essa vai ser a implementação a ser carregada caso tenha mais de 1.
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService { // Serviço relacionado ao token.

    @Value("${jwt.expiration}") // var de ambiente , localização externa - application(test,prod).properties
    private String expiration; // tempo de expiração do token

    @Value("${jwt.secret}") // var de ambiente , localização externa - application(test,prod).properties
    private String secret; // regra de como o token será codificado.

    private final UserMapper userMapper;

    public String generateToken(Authentication authentication) { // Método que gera um token.

        UserMonitoringDTO userDTO = userMapper.toUserMonitoringDTO((User) authentication.getPrincipal()); // Cria um DTO baseado no usuário logado.

        List<String> roles = convertFromObjectListToStringList(userDTO.getRolesDTO()); // Converte uma lista de RoleDTO para string

        try {
            return JWT.create()                              // Cria um token baseado no usuario logado
                    .withIssuer("SecurityStandard API")
                    .withSubject(userDTO.getId())
                    .withClaim("email", userDTO.getEmail())
                    .withClaim("roles", roles)
                    .withIssuedAt(Instant.now())  // !!!!!!!!!! Colocar JWT na frente de Spring boot c Spring Securit readme
                    .withExpiresAt(expirationInstant())
                    .sign(secretAlgorithm());
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Generating jwt token error", exception);
        }
    }

    public String getSubject(String token) { // Método que recupera o subject do token (ID do usuário)
        try {
            return JWT.require(secretAlgorithm())
                    .withIssuer("SecurityStandard API")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (RuntimeException exception) {
            throw new RuntimeException("JWT Token invalid or expired!");
        }
    }

    private List<String> convertFromObjectListToStringList(List<RoleDTO> roles) { // Método para converter uma lista de objetos em uma lista de strings
        return roles.stream().map(String::valueOf).toList();
    }

    private Instant expirationInstant() { // Método que retorna o tempo (QUANDO) o token vai expirar.
        return LocalDateTime.now().plusHours(Long.parseLong(this.expiration))
                .toInstant(ZoneOffset.of("-03:00"));

    }

    private Algorithm secretAlgorithm () { // Algoritmo de codificação.
        return  Algorithm.HMAC256(secret);
    }

}
