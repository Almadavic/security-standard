package com.almadavic.securitystandard.dto.response;

import com.almadavic.securitystandard.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter // Anotação utilizada apenas no teste
@JsonPropertyOrder(value = {"roleName"}) // Estou garantindo a ordem dos atributos no JSON.
public class RoleDTO { // DTO que será retornado pro client, representa as roles dos usuarios.

    @JsonProperty(value = "roleName") // Dando nome para o atributo no JSON
    private final String roleName; // nome da role

    public RoleDTO(Role role) {
        this.roleName = role.getAuthority();
    }

}
