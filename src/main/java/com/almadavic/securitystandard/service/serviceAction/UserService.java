package com.almadavic.securitystandard.service.serviceAction;


import com.almadavic.securitystandard.dto.request.RegisterUserDTO;
import com.almadavic.securitystandard.dto.response.UserMonitoringDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, EncodePassword {  // Service de user deve implementar essa interface.

    UserMonitoringDTO register(RegisterUserDTO registerData); // método com a lógica para se registrar no sistema.

    Page<UserMonitoringDTO> findAll(Pageable pageable, String roleName); // método com a lógica para retornar uma pagina de usuários registrados no sistema.

    UserMonitoringDTO findById(String id); // método com a lógica para retornar um usuário específico do banco pelo id.

}
