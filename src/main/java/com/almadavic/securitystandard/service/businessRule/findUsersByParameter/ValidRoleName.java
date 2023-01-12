package com.almadavic.securitystandard.service.businessRule.findUsersByParameter;


import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class ValidRoleName extends FindUsersByRoleNameVerification {  // Validação caso o client passa um parametro correto como role para o findall

    public ValidRoleName(FindUsersByRoleNameVerification nextOne) {
        super(nextOne);
    }

    @Override
    public Page<User> verification(FindUsersArgs args) {

        String roleName = args.getRoleName();
        Pageable pageable = args.getPageable();
        UserRepository userRepository = args.getUserRepository();

        String roleNamePrefix = "ROLE_" + roleName.toUpperCase();

        boolean validParameter = roleNamePrefix.equalsIgnoreCase("ROLE_ADMIN") ||
                roleNamePrefix.equalsIgnoreCase("ROLE_USER");

        if (validParameter) { // Se a role estiver certa.
            return userRepository.findByRole(pageable, roleNamePrefix); // Vai retornar todos os usuários que contém aquela ROLE
        }

        return nextOne.verification(args); // Caso não aconteça, vai chamar a proxima validação

    }

}
