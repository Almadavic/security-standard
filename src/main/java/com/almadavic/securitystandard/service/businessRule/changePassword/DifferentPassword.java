package com.almadavic.securitystandard.service.businessRule.changePassword;


import com.almadavic.securitystandard.service.customException.SamePasswordException;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
// Indica que essa classe será gerenciada automaticamente pelo Spring em momento de execução, não precisaremos instanciar manualmente.
@Order(2) // Ordem de chamada da classe, no caso de uma lista por exemplo.
public class DifferentPassword implements ChangePasswordVerification { // Regra de négocio que verifica se o usuário está
    // tentando modificar a senha dela para a mesma que a antiga.

    @Override
    public void verification(ChangePasswordArgs args) { // Método que faz a verificação.

        String newPassword = args.changePasswordDTO().getNewPassword(); // Nova senha que o usuário está tentando cadastrar.

        String passwordDataBase = args.changePasswordDTO().getPassword(); // Senha do banco de dados

        if(newPassword.equals(passwordDataBase)) {
            throw new SamePasswordException(); // Se for igual, lança a exception
        }

    }

}
