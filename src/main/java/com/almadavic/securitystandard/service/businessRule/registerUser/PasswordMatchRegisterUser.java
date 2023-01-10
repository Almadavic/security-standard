package com.almadavic.securitystandard.service.businessRule.registerUser;


import com.almadavic.securitystandard.service.exception.PasswordDoesntMatchRegisterUserException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
// Indica que essa classe será gerenciada automaticamente pelo Spring em momento de execução, não precisaremos instanciar manualmente.
@Order(3) // Ordem de chamada da classe, no caso de uma lista por exemplo.
public class PasswordMatchRegisterUser implements RegisterUserVerification {

    @Override
    public void verification(RegisterUserArgs args) {  // Método que faz a verificação.

        String password = args.getRegisterData().getPassword(); // Password inserida pelo usuário
        String confirmationPassword = args.getRegisterData().getConfirmationPassword(); // Password insirida pelo usuário ( tem que ser identica a de cima)

        if (!password.equals(confirmationPassword)) {
            throw new PasswordDoesntMatchRegisterUserException("The passwords don't match"); // Se for diferente, será lançada uma exception.
        }

    }

}
