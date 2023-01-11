package com.almadavic.securitystandard.controller;

import com.almadavic.securitystandard.config.exceptionConfig.standardError.commonStandardError.StandardError;
import com.almadavic.securitystandard.dto.request.RegisterUserDTO;
import com.almadavic.securitystandard.dto.response.UserMonitoringDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@Tag(name = "user" , description = "Operations about user") // Como será chamado o nome do "controller" no swagger e a descrição.
public interface UserController { // Controller User deve implementar essa interface

    @Operation(summary = "Sign up in the System.")
    @ApiResponses(value = {   // Informações relacionadas ao response!
            @ApiResponse(responseCode = "201", description = "User registered",
                    content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "The passwords don't match",
                    content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "500", description = "The e-mail or nickname entered already exist in the system",
                    content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = StandardError.class))}),
    })
    ResponseEntity<String> register(@RequestBody @Valid RegisterUserDTO registerData, UriComponentsBuilder uriBuilder); // Método para se registrar no sistema


    @Operation(summary = "Find all the users from the system.", security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {   // Informações relacionadas ao response!
            @ApiResponse(responseCode = "200", description = "Find users successfully",
                    content = {@Content(mediaType = "application/json" ,
                           array = @ArraySchema( schema = @Schema(implementation = UserMonitoringDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "The parameter {role} in URI is invalid",
                    content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = StandardError.class))}),
    })
    ResponseEntity<Page<UserMonitoringDTO>> findAll(@PageableDefault(sort = "email", direction = Sort.Direction.ASC, size = 12) // Método que retorna uma page de users do sistema.
                                          Pageable pageable, @RequestParam(required = false, value = "role") String roleName);


    @Operation(summary = "Find an user from the system by id.", security = {@SecurityRequirement(name = "bearer-key")})
    @ApiResponses(value = {   // Informações relacionadas ao response!
            @ApiResponse(responseCode = "200", description = "Find user successfully",
                    content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = UserMonitoringDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json" , schema = @Schema(implementation = StandardError.class))}),
    })
    ResponseEntity<UserMonitoringDTO> findById(@PathVariable String id); // Método que retorna um usuário específico do banco por ID.

}
