package com.almadavic.securitystandard.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@AllArgsConstructor //Usado na parte de TESTES -> Para instanciar um DTO!
@Getter
// A classe não precisa de setter nem construtor personalisado, pois o spring já injeta os dados do Json. Foi criada essa linha por causa dos testes.
public class LoginDTO { //  DTO que representa o Login do usuário, email e senha!

    @NotBlank // --> Campo obrigatorio!
    @Email // --> Validação se está em formato de e-mail
    private String email; // email do usuário

    @NotBlank // --> Campo obrigatorio!
    private String password; // senha do usuário

    public UsernamePasswordAuthenticationToken toConvert() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
