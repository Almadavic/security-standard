package com.almadavic.securitystandard.dto.response;

import com.almadavic.securitystandard.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;


@JsonPropertyOrder(value = {"roleName"})
@Getter // Anotação utilizada apenas no teste
public class RoleDTO { // DTO que será retornado pro client, representa as roles dos usuarios.

    @JsonProperty(value = "roleName")
    private final String roleName; // nome da role

    public RoleDTO(Role role) {
        this.roleName = role.getAuthority();
    }

}
