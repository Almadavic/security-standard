package com.almadavic.securitystandard.service.serviceAction;


import com.almadavic.securitystandard.dto.request.ChangePasswordDTO;
import com.almadavic.securitystandard.dto.response.UserDTO;
import com.almadavic.securitystandard.entity.User;

public interface UserAreaService extends EncodePassword {  // Service de userarea deve implementar essa interface.

    UserDTO myProfile(User userLogged); // método com a lógica para visualizar o perfil do usuário logado.

    String changePassword(ChangePasswordDTO cpDTO, User userLogged); // método com a lógica para alterar a senha.

}
