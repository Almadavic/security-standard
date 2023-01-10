package com.almadavic.securitystandard.dto.response;

import com.almadavic.securitystandard.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder(value = {"nickname", "email", "registrationMoment"})
public class UserDTO { // DTO que será retornado pro client, representa o usuário (perfil na visão do usuario).
    @JsonProperty(value = "nickname")
    private final String nickname; // username do usuario
    @JsonProperty(value = "email")
    private final String email; // email do usuario
    @JsonProperty(value = "registrationMoment")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime registrationMoment; // momento de registro do usuário

    public UserDTO(User user) {
        this.nickname = user.getNickname();
        this.email = user.getUsername();
        this.registrationMoment = user.getRegistrationMoment();
    }

}
