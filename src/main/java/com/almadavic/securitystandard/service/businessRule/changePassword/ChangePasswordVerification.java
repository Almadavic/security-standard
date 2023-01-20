package com.almadavic.securitystandard.service.businessRule.changePassword;


import org.springframework.security.crypto.password.PasswordEncoder;


public interface ChangePasswordVerification { // Regras de négocio relacionada a troca de senha.

    // SOLID - Utilizado o Open Closed Principle

    void verification(ChangePasswordArgs args); // As classes que implementam essa interface terão que implementar esse método.

}
