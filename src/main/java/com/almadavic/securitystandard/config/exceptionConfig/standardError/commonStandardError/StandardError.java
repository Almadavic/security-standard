package com.almadavic.securitystandard.config.exceptionConfig.standardError.commonStandardError;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;


@JsonPropertyOrder(value = {"timestamp", "status", "error", "message", "path"})
// ordena como os atributos serão mostrados no JSON.
public class StandardError implements Serializable {   // Classe de formatação de exception! Será retornado o erro de uma forma mais agradável para o cliente.
    // Classe de formatação de erros comuns

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "GMT-3")
    //  O tempo aparecer formatado pro cliente (JSON).
    @JsonProperty(value = "timestamp") // -> nome do campo no JSON
    private final Instant timestamp; // -> Tempo em que o erro ocorreu.

    @JsonProperty(value = "status") // -> nome do campo no JSON
    private final Integer status; // -> Status HTTP

    @JsonProperty(value = "error") // -> nome do campo no JSON
    private final String error;   // -> Erro

    @JsonProperty(value = "message") // -> nome do campo no JSON
    private final String message; // -> Mensagem do erro

    @JsonProperty(value = "path") // -> nome do campo no JSON
    private final String path; // -> URI da requisição

    public StandardError(Integer status, String error, String message, String path) {
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}
