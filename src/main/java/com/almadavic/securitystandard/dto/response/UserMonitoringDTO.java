package com.almadavic.securitystandard.dto.response;

import com.almadavic.securitystandard.entity.Role;
import com.almadavic.securitystandard.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonPropertyOrder(value = {"id", "nickname", "email", "roles", "registrationMoment"})
public class UserMonitoringDTO extends UserDTO { // DTO que será retornado pro client, representa o usuário ( na visão do administrador).

    @JsonProperty(value = "id")
    private final String id; // id do usuário

    @JsonProperty(value = "roles")
    private final List<RoleDTO> rolesDTO; // roles do usuario

    public UserMonitoringDTO(User user) {
        super(user);
        this.id = user.getId();
        this.rolesDTO = convertListFromEntityToDTO(user.getAuthorities());
    }

    private List<RoleDTO> convertListFromEntityToDTO(List<Role> roles) {   // Método para converter uma lista de Role para RoleDTO
        return roles.stream().map(RoleDTO::new).collect(Collectors.toList());
    }

}
