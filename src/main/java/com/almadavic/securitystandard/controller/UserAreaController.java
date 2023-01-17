package com.almadavic.securitystandard.controller;


import com.almadavic.securitystandard.config.exceptionConfig.standardError.commonStandardError.StandardError;
import com.almadavic.securitystandard.dto.request.ChangePasswordDTO;
import com.almadavic.securitystandard.dto.response.UserDTO;
import com.almadavic.securitystandard.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "user-area", description = "The user area, where the own user performs operations")
// Como será chamado o nome do "controller" no swagger e a descrição.
public interface UserAreaController { // Controller de UserArea deve implementar essa interface

    @Operation(summary = "Access the logged user informations", security = {@SecurityRequirement(name = "bearer-key")})
    // SecurityRequirement - Segurança de metodo no Swagger
    @ApiResponses(value = {                 // Informações relacionadas ao response!
            @ApiResponse(responseCode = "200", description = "Return the user profile",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "401", description = "You have to be logged in the system in order to access this resource",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))})
    })
    ResponseEntity<UserDTO> myProfile(@AuthenticationPrincipal @Parameter(hidden = true) User userLogged); // Método para ver dados do proprio usuário logado no sistema.


    @Operation(summary = "Change the account password", security = {@SecurityRequirement(name = "bearer-key")})
    // SecurityRequirement - Segurança de metodo no Swagger
    @ApiResponses(value = {                 // Informações relacionadas ao response!
            @ApiResponse(responseCode = "200", description = "Password changed",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Password entered doesn't match the user password in database",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))}),
            @ApiResponse(responseCode = "500", description = "The new password cannot be equal the last one",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))}),
    })
    ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordDTO cpDTO, @AuthenticationPrincipal
    @Parameter(hidden = true) User userLogged); // Método para alterar a senha do usuário no sistema.

}
