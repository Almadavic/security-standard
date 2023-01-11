package com.almadavic.securitystandard.controller;


import com.almadavic.securitystandard.dto.request.RegisterUserDTO;
import com.almadavic.securitystandard.dto.response.UserMonitoringDTO;
import com.almadavic.securitystandard.service.serviceAction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@RestController   // Identificando  que é um rest-controller
@RequestMapping(value = "/users")   // Recurso para "encontrar" esse controller
public class UserControllerImpl implements UserController { // Controller para o ADM controlar os usuários do sistema e para o Usuário se cadastrar, como um CRUD

    private final UserService userService;

    @Override
    @PostMapping(value = "/register") // Método HTTP POST - Cadastrar / Criar
    public ResponseEntity<String> register(RegisterUserDTO registerData, UriComponentsBuilder uriBuilder) { // Método para se registrar no sistema

        UserMonitoringDTO userDTO = userService.register(registerData);

        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(userDTO.getId()).toUri();

        String message = userDTO.getNickname() + ", your account was registered successfully!";

        return ResponseEntity.created(uri).body(message);
    }

    @Override
    @GetMapping // Método HTTP GET -> Obter / Recuperar
    public ResponseEntity<Page<UserMonitoringDTO>> findAll(Pageable pageable, String roleName) {// Método que retorna uma page de users do sistema.

        Page<UserMonitoringDTO> usersDTO = userService.findAll(pageable, roleName);

        return ResponseEntity.ok().body(usersDTO);
    }

    @Override
    @GetMapping(value = "/{id}") // Método HTTP GET -> Obter / Recuperar
    public ResponseEntity<UserMonitoringDTO> findById(String id) { // Método quen retorna um usuário especifico do sistema pelo ID.

        UserMonitoringDTO userDTO = userService.findById(id);

        return ResponseEntity.ok().body(userDTO);
    }

}
