package com.almadavic.securitystandard.service.businessRule.changePassword;


import com.almadavic.securitystandard.service.customException.DatabaseException;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
// Indica que essa classe será gerenciada automaticamente pelo Spring em momento de execução, não precisaremos instanciar manualmente.
@Order(1) // Ordem de chamada da classe, no caso de uma lista por exemplo.
public class PasswordMatchsDataBasePassword implements ChangePasswordVerification { // Regra de négocio que verifica se o
    // usuário está digitando sua senha corretamente para poder muda-lá.

    @Override
    public void verification(ChangePasswordArgs args) { // Método que faz a verificação

        String passwordDTO = args.getCpDTO().getPassword(); // Senha para o usuário digitar ( que seja igual a do banco)

        String passwordDataBase = args.getUser().getPassword(); // Senha do banco de dados.

        PasswordEncoder encoder = args.getEncoder();

        if (!check(passwordDTO, passwordDataBase, encoder)) {
            throw new DatabaseException("The password is not correct (not match)"); // Se for diferente, lança a exception
        }

    }

}
