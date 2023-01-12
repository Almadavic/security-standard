package com.almadavic.securitystandard.config.securityConfig;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
@JsonPropertyOrder(value = {"token", "type"}) // Estou garantindo a ordem dos atributos no JSON.
public class Token { // Essa classe tem como objetivo retornar para o client o token(que armazena os dados do user "logado")
    // e o type pro usuário quando ele se autentica.

    @JsonProperty(value = "token")  // Dando nome para o atributo no JSON
    private String token;

    @JsonProperty(value = "type")  // Dando nome para o atributo no JSON
    private String type;

}
