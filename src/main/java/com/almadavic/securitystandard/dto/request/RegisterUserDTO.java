package com.almadavic.securitystandard.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor //Usado na parte de TESTES -> Para instanciar um DTO!
@Getter
@JsonPropertyOrder(value = {"nickname", "email", "password", "confirmationPassword"})
public class RegisterUserDTO { // DTO que representa o cadastro do usuário no sistema.

    @NotBlank // --> Campo obrigatorio!
    @JsonProperty(value = "nickname")
    private String nickname; // apelido (username) para ser cadastrado

    @NotBlank // --> Campo obrigatorio!
    @Email // --> Validação se está em formato de e-mail
    @JsonProperty(value = "email")
    private String email; // email do usuário

    @Size(min = 6, max = 18) // --> Validação do tamanho da senha
    @JsonProperty(value = "password")
    private String password; // nova senha para ser cadastrada

    @NotBlank // --> Campo obrigatorio!
    @JsonProperty(value = "confirmationPassword")
    private String confirmationPassword; // confirmação de senha

}
