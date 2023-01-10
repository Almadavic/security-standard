package com.almadavic.securitystandard.service.businessRule.changePassword;

import com.almadavic.securitystandard.dto.request.ChangePasswordDTO;
import com.almadavic.securitystandard.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Getter
public class ChangePasswordArgs {

    private ChangePasswordDTO cpDTO;
    private User user;
    private PasswordEncoder encoder;

}
