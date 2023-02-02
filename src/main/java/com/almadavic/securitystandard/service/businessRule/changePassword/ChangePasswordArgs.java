package com.almadavic.securitystandard.service.businessRule.changePassword;


import com.almadavic.securitystandard.dto.request.ChangePasswordDTO;
import com.almadavic.securitystandard.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

public record ChangePasswordArgs(ChangePasswordDTO changePasswordDTO, User user,
                                 PasswordEncoder encoder) { // Argumentos necessários para poder verificar se o o usuário pode mudar sua senha.

}
