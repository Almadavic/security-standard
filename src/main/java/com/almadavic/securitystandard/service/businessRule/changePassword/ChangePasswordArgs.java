package com.almadavic.securitystandard.service.businessRule.changePassword;


import com.almadavic.securitystandard.dto.request.ChangePasswordDTO;
import com.almadavic.securitystandard.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;


@AllArgsConstructor
@Getter
public class ChangePasswordArgs { // Argumentos necessários para poder trocar a senha no sistema.

    private ChangePasswordDTO cpDTO; // Senha antiga e nova.
    private User user; // Usuário logado.
    private PasswordEncoder encoder; // Codificador de senha.

}
