package com.almadavic.securitystandard.dto.response;


import com.almadavic.securitystandard.entity.Role;
import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.util.UserMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


@JsonPropertyOrder(value = {"id", "nickname", "email", "roles", "registrationMoment"})
@Getter
// Estou garantindo a ordem dos atributos no JSON.
public class UserMonitoringDTO extends UserDTO { // DTO que será retornado pro client, representa o usuário ( na visão do administrador).

    @JsonProperty(value = "id")  // Dando nome para o atributo no JSON
    private final String id; // id do usuário

    @JsonProperty(value = "roles")  // Dando nome para o atributo no JSON
    private final List<RoleDTO> rolesDTO; // roles do usuario

    public UserMonitoringDTO(User user) {
        super(user);
        this.id = user.getId();
        this.rolesDTO = UserMapper.toRoleDTO(user.getAuthorities());
    }

}
