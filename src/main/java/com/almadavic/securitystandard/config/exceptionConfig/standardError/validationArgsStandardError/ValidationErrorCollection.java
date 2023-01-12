package com.almadavic.securitystandard.config.exceptionConfig.standardError.validationArgsStandardError;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder(value = {"timestamp", "status", "error", "validationErrorList", "path"})
// ordena como os atributos serão mostrados no JSON.
public class ValidationErrorCollection implements Serializable { // Classe que será uma lista de exceptions de validation.

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "GMT-3")
    // Formatação da data no JSON
    @JsonProperty(value = "timestamp") // -> nome do campo no JSON
    private final Instant timestamp;

    @JsonProperty(value = "status") // -> nome do campo no JSON
    private final Integer status;

    @JsonProperty(value = "path") // -> nome do campo no JSON
    private final String path;

    @JsonProperty(value = "error") // -> nome do campo no JSON
    private final String error;

    @JsonProperty(value = "validationErrorList") // -> nome do campo no JSON
    private final List<StandardErrorArgsNotValid> validationErrorList = new ArrayList<>();

    public ValidationErrorCollection(Integer status, String path, String error) {
        this.timestamp = Instant.now();
        this.status = status;
        this.path = path;
        this.error = error;
    }

    public void addStandardErrorArgsNotValid(StandardErrorArgsNotValid standard) {
        validationErrorList.add(standard);
    }

}
