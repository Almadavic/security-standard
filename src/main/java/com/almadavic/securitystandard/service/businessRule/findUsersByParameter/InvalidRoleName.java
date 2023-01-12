package com.almadavic.securitystandard.service.businessRule.findUsersByParameter;


import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.service.customException.InvalidParamException;
import org.springframework.data.domain.Page;


public class InvalidRoleName extends FindUsersByRoleNameVerification { // Validação caso o client passa um parametro errado como role para o findall

    public InvalidRoleName() {
        super(null);
    }

    @Override
    public Page<User> verification(FindUsersArgs args) {

        String roleName = args.getRoleName();

        throw new InvalidParamException("This parameter (role) : { " + roleName + " } is invalid"); // Se chegar até aqui nessa classe, é porque o parametro está inválido

    }

}
