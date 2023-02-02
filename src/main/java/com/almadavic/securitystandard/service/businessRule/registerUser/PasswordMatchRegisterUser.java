package com.almadavic.securitystandard.service.businessRule.registerUser;


import com.almadavic.securitystandard.service.customException.PasswordDoesntMatchRegisterUserException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
// Indica que essa classe será gerenciada automaticamente pelo Spring em momento de execução, não precisaremos instanciar manualmente.
@Order(3) // Ordem de chamada da classe, no caso de uma lista por exemplo.
public class PasswordMatchRegisterUser implements RegisterUserVerification { // Verifica se as senhas que o usuário passou para se cadastrar são iguais.

    @Override
    public void verification(RegisterUserArgs args) {  // Método que faz a verificação.

        String password = args.registerData().getPassword(); // Password inserida pelo usuário
        String confirmationPassword = args.registerData().getConfirmationPassword(); // Password de confirmação insirida pelo usuário ( tem que ser identica a de cima)

        if (!password.equals(confirmationPassword)) {
            throw new PasswordDoesntMatchRegisterUserException(); // Se for diferente, será lançada uma exception.
        }

    }

}
