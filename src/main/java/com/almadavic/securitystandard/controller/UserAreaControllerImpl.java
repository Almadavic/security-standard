package com.almadavic.securitystandard.controller;


import com.almadavic.securitystandard.dto.request.ChangePasswordDTO;
import com.almadavic.securitystandard.dto.response.UserDTO;
import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.service.serviceAction.UserAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@RestController   // Identificando  que é um rest-controller
@RequestMapping(value = "/userarea")   // Recurso para "encontrar" esse controller
public class UserAreaControllerImpl implements UserAreaController { // Controller onde os usuários interagem, (AREA DO USUARIO)

    private final UserAreaService userAreaService; // Injeção de dependencia automatica - > UserAreaService

    @Override
    @GetMapping(value = "/myprofile") // Método HTTP GET -> Obter / Recuperar
    public ResponseEntity<UserDTO> myProfile(User userLogged) { // Método para visualizar "area do usuario" no sistema, ver dados do usuário logado

        UserDTO userDTO = userAreaService.myProfile(userLogged);

        return ResponseEntity.ok().body(userDTO);
    }

    @Override
    @PutMapping(value = "/changepassword") // Método HTTP PUT - > ALTERAR / UPDATE
    public ResponseEntity<String> changePassword(ChangePasswordDTO cpDTO, User userLogged) { // Método para alterar a senha da conta no sistema.

        String message = userAreaService.changePassword(cpDTO, userLogged);

        return ResponseEntity.ok().body(message);
    }

}
