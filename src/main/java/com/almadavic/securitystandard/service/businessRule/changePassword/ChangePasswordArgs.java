package com.almadavic.securitystandard.service.businessRule.changePassword;


import com.almadavic.securitystandard.dto.request.ChangePassword;
import com.almadavic.securitystandard.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public record ChangePasswordArgs(ChangePassword changePasswordDTO, User user,
                                 PasswordEncoder encoder) { // Argumentos necessários para poder verificar se o o usuário pode mudar sua senha.

}
