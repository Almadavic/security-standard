package com.almadavic.securitystandard.util;

import com.almadavic.securitystandard.dto.request.RegisterUserDTO;
import com.almadavic.securitystandard.dto.response.RoleDTO;
import com.almadavic.securitystandard.dto.response.UserDTO;
import com.almadavic.securitystandard.dto.response.UserMonitoringDTO;
import com.almadavic.securitystandard.entity.Role;
import com.almadavic.securitystandard.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper { // Classe para fazer algumas conversões de DTO para User e User para DTO.

    public User toUserEntity(RegisterUserDTO registerData, PasswordEncoder encoder, Role role) {

        return User.builder()
                .nickname(registerData.getNickname())
                .email(registerData.getEmail())
                .password(encoder.encode(registerData.getPassword())) // encoder --> codifica a senha ( mais informações na declaração do método).
                .role(role)
                .build();  // -> Utilizado um builder na contrução do objeto pois é mais légivel e evita passar parametros errados.

    }

    public UserDTO toUserDTO(User user) { // Converte uma entidade usuário em usuário DTO.
        return new UserDTO(user);
    }

    public UserMonitoringDTO toUserMonitoringDTO(User user) { // Converte uma entidade usuário em Usuário DTO de monitoramento.
        return new UserMonitoringDTO(user);
    }

    public Page<UserMonitoringDTO> toUserMonitoringDTO(Page<User> users) { // Método converte uma página de usuários (entidade) para uma página de usuários DTO.
        return users.map(UserMonitoringDTO::new); // Convertendo de entidade para DTO cada elemento da lista e retornando essa nova lista DTO.
    }

    public static List<RoleDTO> toRoleDTO(List<Role> roles) {   // Método para converter uma lista de Role para RoleDTO
        return roles.stream().map(RoleDTO::new).collect(Collectors.toList());
    }

}
