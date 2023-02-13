package com.almadavic.securitystandard.service.businessRule.registerUser;


import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.repository.UserRepository;
import com.almadavic.securitystandard.service.customException.NicknameAlreadyRegisteredException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
// Indica que essa classe será gerenciada automaticamente pelo Spring em momento de execução, não precisaremos instanciar manualmente.
@Order(1) // Ordem de chamada da classe, no caso de uma lista por exemplo.
public class NicknameAlreadyRegistered implements RegisterUserVerification { // Se o usuário já tiver registrada no sistema (email), outra n pode registrar com o msm email.

    @Override
    public void verification(RegisterUserArgs args) { // Método que faz a verificação.

        String nickname = args.registerData().getNickname(); // username com que o usuário pretende se cadastrar.
        UserRepository userRepository = args.userRepository(); // Repository para se conectar com o banco.

        Optional<User> user = userRepository.findByNickname(nickname); // Retorna um Optional indicando se já existe algum usuário com aquele nickname no banco.

        if (user.isPresent()) {
            throw new NicknameAlreadyRegisteredException(nickname); // Se tiver, vai dar erro, pois não pode ter 2 nicknames iguais.
        }

    }

}
