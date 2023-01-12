package com.almadavic.securitystandard.service.businessRule.findUsersByParameter;


import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class NoRoleName extends FindUsersByRoleNameVerification {  // Validação caso o client não passe nenhum parametro  como role para o findall

    public NoRoleName(FindUsersByRoleNameVerification nextOne) {
        super(nextOne);
    }

    @Override
    public Page<User> verification(FindUsersArgs args) {

        String roleName = args.getRoleName();
        Pageable pageable = args.getPageable();
        UserRepository userRepository = args.getUserRepository();

        if (roleName == null) { // Se não passar nenhum parametro
            return userRepository.findAll(pageable); // Vai retornar o page normal
        }

        return nextOne.verification(args); // Caso não, vai para a proxima validação

    }

}
