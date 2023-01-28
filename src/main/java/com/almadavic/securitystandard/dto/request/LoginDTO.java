package com.almadavic.securitystandard.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@AllArgsConstructor //Usado na parte de TESTES -> Para instanciar um DTO!
@Getter
@JsonPropertyOrder(value = {"email", "password"})
public class LoginDTO { //  DTO que representa o Login do usuário, email e senha!

    @NotBlank // --> Campo obrigatorio!
    @Email // --> Validação se está em formato de e-mail
    @JsonProperty(value = "email")
    private String email; // email do usuário

    @NotBlank // --> Campo obrigatorio!
    @JsonProperty(value = "password")
    private String password; // senha do usuário

    public UsernamePasswordAuthenticationToken toConvert() {   // Converter esses dados informados pelo usuário em um token.
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
