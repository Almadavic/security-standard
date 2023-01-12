package com.almadavic.securitystandard.service.businessRule.registerUser;


import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.repository.UserRepository;
import com.almadavic.securitystandard.service.customException.EmailAlreadyRegisteredException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
// Indica que essa classe será gerenciada automaticamente pelo Spring em momento de execução, não precisaremos instanciar manualmente.
@Order(2) // Ordem de chamada da classe, no caso de uma lista por exemplo.
public class EmailAlreadyRegistered implements RegisterUserVerification { // Se a pessoa já tiver registrada no sistema (email), outra n pode registrar com o msm email.

    @Override
    public void verification(RegisterUserArgs args) { // Método que faz a verificação.

        String email = args.getRegisterData().getEmail(); // e-mail com que o usuário pretende se cadastrar no sistema.
        UserRepository userRepository = args.getUserRepository();

        Optional<User> user = userRepository.findByEmail(email); // Retorna um Optional se já existe algum usuário com aquele e-mail no banco.

        if (user.isPresent()) {
            throw new EmailAlreadyRegisteredException("This e-mail: " + email + " already exists in the system"); // Se tiver, vai dar erro, pois não pode ter 2 e-mails iguais.
        }

    }

}
