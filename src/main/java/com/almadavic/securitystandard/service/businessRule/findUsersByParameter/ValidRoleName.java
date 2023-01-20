package com.almadavic.securitystandard.service.businessRule.findUsersByParameter;


import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class ValidRoleName extends FindUsersByRoleNameVerification {  // Validação caso o client passa um parametro correto como role para o findAll

    public ValidRoleName(FindUsersByRoleNameVerification nextOne) {
        super(nextOne);
    }

    @Override
    public Page<User> verification(FindUsersArgs args) {

        String roleName = args.getRoleName(); // role passada pelo usuário.
        Pageable pageable = args.getPageable(); // Paginação.
        UserRepository userRepository = args.getUserRepository(); // repository para retornar a página.

        String role = "ROLE_" + roleName.toUpperCase();

        boolean validParameter = validParameter(role);

        if (validParameter) { // Se a role estiver certa.
            return userRepository.findByRole(pageable, role); // Vai retornar todos os usuários que contém aquela ROLE
        }

        return nextOne.verification(args); // Caso não aconteça, vai chamar a proxima validação (da proxima classe).

    }

    private boolean validParameter (String role) { // Método para verificar se a role passada pelo usuário é válida.
        return role.equalsIgnoreCase("ROLE_ADMIN") ||  role.equalsIgnoreCase("ROLE_USER");
    }

}
