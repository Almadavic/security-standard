package com.almadavic.securitystandard.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;





@AllArgsConstructor
@Getter
// A classe não precisa de setter nem construtor personalisado, pois o spring já injeta os dados do Json. Foi criada essa linha por causa dos testes.
@JsonPropertyOrder(value = {"yourPassword", "newPassword"})
public class ChangePasswordDTO { // DTO que representa a mudança de senha do usuário

    @NotBlank // --> Campo obrigatorio!
    @JsonProperty(value = "yourPassword")
    private String password; // Password do usuário logado.

    @NotBlank // --> Campo obrigatorio!
    @Size(min = 6, max = 18) // --> Validação do tamanho da senha
    @JsonProperty(value = "newPassword")
    private String newPassword; // Nova senha.
}