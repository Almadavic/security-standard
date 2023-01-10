package com.almadavic.securitystandard.service.businessRule.findUsersByParameter;


import com.almadavic.securitystandard.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
public abstract class FindUsersByRoleNameVerification { // Design Patterns - Chain of Responsibility
    // Classe faz com que as classes filhas tenham o atributo e o método no construtor, vai fazendo validações dentro das classes até achar um return correto.

    protected FindUsersByRoleNameVerification nextOne;

    public abstract Page<User> verification(FindUsersArgs args);

}
