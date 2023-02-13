package com.almadavic.securitystandard.service.serviceAction;


import com.almadavic.securitystandard.dto.request.ChangePassword;
import com.almadavic.securitystandard.dto.response.UserDTO;
import com.almadavic.securitystandard.entity.User;


public interface UserAreaService {  // Service de userArea deve implementar essa interface.

    UserDTO myProfile(User userLogged); // método com a lógica para visualizar o perfil do usuário logado.

    String changePassword(ChangePassword cpDTO, User userLogged); // método com a lógica para alterar a senha.

}
